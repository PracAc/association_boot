package org.oz.association_boot.applier.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.applier.domain.ApplierEntity;
import org.oz.association_boot.applier.dto.*;
import org.oz.association_boot.applier.repository.ApplierEntityRepository;
import org.oz.association_boot.common.domain.AttachFile;
import org.oz.association_boot.common.dto.PageResponseDTO;
import org.oz.association_boot.kafka.dto.AuthProducerDTO;
import org.oz.association_boot.kafka.producer.AssociationProducer;
import org.oz.association_boot.kafka.util.KafkaJsonUtil;
import org.oz.association_boot.util.authcode.AuthCodeUtil;
import org.oz.association_boot.mail.dto.MailHtmlSendDTO;
import org.oz.association_boot.mail.MailSendService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ApplierService {

    private final ApplierEntityRepository applierEntityRepository;
    private final MailSendService mailSendService;
    private final AuthCodeUtil authCodeUtil;
    private final PasswordEncoder passwordEncoder;
    private final AssociationProducer associationProducer;
    private final KafkaJsonUtil kafkaJsonUtil;

    public PageResponseDTO<ApplierListDTO> getApplierList (ApplierListRequestDTO pageRequestDTO){

        log.info("===========================getApplierList========================");


        return applierEntityRepository.getListApplier(pageRequestDTO);
    }

    public Optional<ApplierReadDTO> getApplierOne (Long ano){

        ApplierEntity result = applierEntityRepository.getApplierWithFiles(ano).get();
        List<String> fileNames = result.getAttachFiles()
                .stream()
                .map(AttachFile::getFileName)
                .collect(Collectors.toList());

        return Optional.of(ApplierReadDTO.builder()
                .ano(result.getAno())
                .email(result.getEmail())
                .name(result.getName())
                .bizNo(result.getBizNo())
                .openDate(result.getOpenDate())
                .regStatus(result.getRegStatus())
                .regDate(result.getRegDate())
                .attachFileNames(fileNames)
                .build());
    }

    public Optional<Long> registryApplier(ApplierRegistryDTO registryDTO){
        ApplierEntity applierEntity = ApplierEntity.builder()
                .bizNo(registryDTO.getBizNo())
                .name(registryDTO.getName())
                .openDate(registryDTO.getOpenDate())
                .email(registryDTO.getEmail())
                .phone(registryDTO.getPhone())
                .build();

        registryDTO.getUploadFileNames().forEach(fileName -> {
            applierEntity.addFile(fileName);
            log.info(fileName);
        });

        applierEntityRepository.save(applierEntity);

        return Optional.of(applierEntity.getAno());
    }

    public Optional<Long> modifyStatus(ApplierModifyDTO modifyDTO){
        ApplierEntity applierEntity = applierEntityRepository.getApplier(modifyDTO.getAno()).get();

        if (modifyDTO.getStatus() == 1){
            // 6자리 영문대소문자 숫자 랜덤생성 + ano
            String authCode = authCodeUtil.generateTextAuthCode(6) + applierEntity.getAno();

            applierEntity.changeAccepted(); // 승인으로 상태변경
            applierEntity.changeAuthCode(authCode); // 인증코드 수정

            // 보내게 될 메세지 지정
            MailHtmlSendDTO sendDTO = MailHtmlSendDTO.builder()
                    .ano(applierEntity.getAno())
                    .cname(applierEntity.getName())
                    .subject(applierEntity.getName() + "님 협회등록 승인안내")
                    .content("정상적인 등록을 위해 아래 경로를 통한 인증을 해주시길 바랍니다.")
                    .emailAddr(applierEntity.getEmail())
                    .build();

            mailSendService.sendAuthCodeMail(sendDTO,authCode);
        }
        if (modifyDTO.getStatus() == 2){
            applierEntity.changeRejected(); // 상태변경
        }

        return Optional.of(applierEntity.getAno());
    }

    public Optional<String> checkAuth(ApplierAuthCheckDTO checkDTO){
        ApplierEntity applierEntity = applierEntityRepository.getApplier(checkDTO.getAno()).get();

        log.info(checkDTO);
        log.info(applierEntity.getEmail());
        log.info(checkDTO.getEmail());
        log.info(applierEntity.getEmail().equals(checkDTO.getEmail()));

        if (applierEntity.getAuthCheck()){
            return Optional.of("이미 인증처리가 되었습니다.");
        }
        if (! applierEntity.getEmail().equals(checkDTO.getEmail())){
            return Optional.of("이메일이 맞지 않습니다.");
        }
        if (! applierEntity.getAuthCode().equals(checkDTO.getAuthCode())){
            return Optional.of("인증코드가 맞지 않습니다.");
        }

        applierEntity.changeAuthCheck(true);

        String email = applierEntity.getEmail();
        String creatorId = email.substring(0, email.indexOf("@")) + applierEntity.getAno();
        String password = passwordEncoder.encode(applierEntity.getAuthCode());

        AuthProducerDTO authProducerDTO = AuthProducerDTO.builder()
                .creatorId(creatorId)
                .creatorName(applierEntity.getName())
                .creatorEmail(email)
                .creatorPhone(applierEntity.getPhone())
                .creatorPassword(password)
                .build();

        // Kafka Producer 작동 부분
        try {
            Object jsonString = kafkaJsonUtil.dtoToJson(authProducerDTO);
            associationProducer.sendMessage(jsonString);

        }catch (IOException e){
            return Optional.of("인증 처리중 문제가 생겼습니다. \n확인 후 다시 시도해주시길 바랍니다.");
        }

        return Optional.of("정상적으로 인증 되었습니다.");
    }
}
