package org.oz.association_boot.applier.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.applier.domain.ApplierEntity;
import org.oz.association_boot.applier.dto.ApplierListDTO;
import org.oz.association_boot.applier.dto.ApplierReadDTO;
import org.oz.association_boot.applier.repository.ApplierEntityRepository;
import org.oz.association_boot.common.domain.AttachFile;
import org.oz.association_boot.common.dto.PageRequestDTO;
import org.oz.association_boot.common.dto.PageResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ApplierService {

    private final ApplierEntityRepository applierEntityRepository;

    public PageResponseDTO<ApplierListDTO> getApplierList (PageRequestDTO pageRequestDTO){

        log.info("===========================getApplierList========================");


        return applierEntityRepository.getListApplier(pageRequestDTO);
    }

    public Optional<ApplierReadDTO> getApplierOne (Long ano){

        ApplierEntity result = applierEntityRepository.getApplier(ano).get();
        List<String> fileNames = result.getAttachFiles()
                .stream()
                .map(AttachFile::getFileName)
                .collect(Collectors.toList());

        return Optional.of(ApplierReadDTO.builder()
                .ano(result.getAno())
                .email(result.getEmail())
                .name(result.getName())
                .zipcode(result.getZipcode())
                .roadAddr(result.getRoadAddr())
                .lotNumAddr(result.getLotNumAddr())
                .detailAddr(result.getDetailAddr())
                .addrEtc(result.getAddrEtc())
                .regStatus(result.getRegStatus())
                .regDate(result.getRegDate())
                .attachFileNames(fileNames)
                .build());
    }
}
