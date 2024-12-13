package org.oz.association_boot.applier.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.applier.domain.ApplierEntity;
import org.oz.association_boot.applier.domain.ApplierHistoryEntity;
import org.oz.association_boot.applier.dto.*;
import org.oz.association_boot.applier.repository.ApplierEntityRepository;
import org.oz.association_boot.applier.repository.ApplierHistoryEntityRepository;
import org.oz.association_boot.common.domain.AttachFile;
import org.oz.association_boot.common.dto.PageResponseDTO;
import org.oz.association_boot.kafka.dto.AuthProducerDTO;
import org.oz.association_boot.kafka.producer.AssociationProducer;
import org.oz.association_boot.kafka.util.KafkaJsonUtil;
import org.oz.association_boot.util.authcode.AuthCodeUtil;
import org.oz.association_boot.mail.dto.MailHtmlSendDTO;
import org.oz.association_boot.mail.MailService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ApplierService {

    private final ApplierEntityRepository applierEntityRepository;
    private final ApplierHistoryEntityRepository historyEntityRepository;
    private final MailService mailService;
    private final AuthCodeUtil authCodeUtil;
    private final PasswordEncoder passwordEncoder;
    private final AssociationProducer associationProducer;
    private final KafkaJsonUtil kafkaJsonUtil;
    private final Map<String, String> emailAuthCodes = new HashMap<>();

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

        applierEntity.setCreator(registryDTO.getName());

        registryDTO.getUploadFileNames().forEach(fileName -> {
            applierEntity.addFile(fileName);
        });
        // 등록시 이메일 인증 시도한 Map remove처리
        emailAuthCodes.remove(registryDTO.getEmail());

        applierEntityRepository.save(applierEntity);

        // 등록 DB저장 완료시 History 테이블 저장
        ApplierHistoryEntity historyEntity = ApplierHistoryEntity.builder()
                .email(registryDTO.getEmail())
                .name(registryDTO.getName())
                .modifier(registryDTO.getName())
                .status("Registry")
                .build();

        historyEntityRepository.save(historyEntity);

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

            mailService.sendAuthCodeMail(sendDTO,authCode);

            // 등록 승인시 히스토리
            ApplierHistoryEntity historyEntity = ApplierHistoryEntity.builder()
                    .email(applierEntity.getEmail())
                    .name(applierEntity.getName())
//                    .modifier()
                    .status("Accepted")
                    .build();

            historyEntityRepository.save(historyEntity);
        }

        if (modifyDTO.getStatus() == 2){
            applierEntity.changeRejected(); // 상태변경

            MailHtmlSendDTO sendDTO = MailHtmlSendDTO.builder()
                    .ano(applierEntity.getAno())
                    .cname(applierEntity.getName())
                    .subject(applierEntity.getName() + "님 협회등록 반려안내")
                    .content(applierEntity.getName() + "님 협회등록 반려에 대한 내용입니다.")
                    .rejectReason(modifyDTO.getRejectReason())
                    .emailAddr(applierEntity.getEmail())
                    .build();

            mailService.sendHtmlMail(sendDTO);

            // 등록 반려시 히스토리
            ApplierHistoryEntity historyEntity = ApplierHistoryEntity.builder()
                    .email(applierEntity.getEmail())
                    .name(applierEntity.getName())
//                    .modifier()
                    .status("Rejected")
                    .build();

            historyEntityRepository.save(historyEntity);
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

    public Optional<String> sendEmailAuth(String email){
        String authCode = authCodeUtil.generateTextAuthCode(6);


        // 메모리상 이메일, 인증코드 맵객체 저장
        emailAuthCodes.put(email, authCode);

        // 보내게 될 메세지 지정
        MailHtmlSendDTO sendDTO = MailHtmlSendDTO.builder()
                .subject("부산 지역 아티스트 협회 이메일 인증")
                .content("요청하신 인증 코드입니다")
                .emailAddr(email)
                .build();

        mailService.sendEmailAuthCodeMail(sendDTO,authCode);

        return Optional.of("이메일이 전송 되었습니다.");
    }

    public Optional<Boolean> checkEmailAuth(String email, String authCode){

        String storedCode = emailAuthCodes.get(email);

        if (storedCode != null && storedCode.equals(authCode)){

            return Optional.of(true);
        }
        return Optional.of(false);
    }

    // 현재 인증된 사용자의 username을 가져오는 메서드
    private String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증된 사용자가 있으면, 사용자 이름을 반환
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();  // 일반적으로 username이 반환됨
        }

        return null;  // 인증되지 않은 경우 null 반환
    }
}
