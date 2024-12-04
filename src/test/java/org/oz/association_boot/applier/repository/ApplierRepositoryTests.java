package org.oz.association_boot.applier.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.oz.association_boot.applier.domain.ApplierEntity;
import org.oz.association_boot.applier.domain.ApplierStatus;
import org.oz.association_boot.applier.dto.ApplierListDTO;
import org.oz.association_boot.applier.dto.ApplierModifyDTO;
import org.oz.association_boot.applier.dto.ApplierReadDTO;
import org.oz.association_boot.common.domain.AttachFile;
import org.oz.association_boot.common.dto.PageRequestDTO;
import org.oz.association_boot.common.dto.PageResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootTest
@Log4j2
public class ApplierRepositoryTests {
    @Autowired
    ApplierEntityRepository applierEntityRepository;

    @Test
    public void insertTest() {

        for (int i = 0; i < 150; i++) {
            ApplierEntity applierEntity = ApplierEntity.builder()
                    .name("Creator" + i)
                    .bizNo("123456789" + i)
                    .openDate("2024-12-12")
                    .email("skwize@naver.com")
                    .regStatus(ApplierStatus.PENDING)
                    .build();
            applierEntity.addFile(UUID.randomUUID()+"test.jpg");
            applierEntity.addFile(UUID.randomUUID()+"test.jpg");

            applierEntityRepository.save(applierEntity);
        }//end for

    }

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<ApplierListDTO> response = applierEntityRepository.getListApplier(pageRequestDTO);

        log.info(response);
    }

    @Test
    public void getReadTest() {
        Long ano = 149L;

        ApplierEntity result = applierEntityRepository.getApplier(ano).get();
        List<String> fileNames = result.getAttachFiles()
                .stream()
                .map(AttachFile::getFileName)
                .collect(Collectors.toList());

        ApplierReadDTO dto = ApplierReadDTO.builder()
                .ano(result.getAno())
                .email(result.getEmail())
                .name(result.getName())
                .bizNo(result.getBizNo())
                .openDate(result.getOpenDate())
                .regStatus(result.getRegStatus())
                .regDate(result.getRegDate())
                .attachFileNames(fileNames)
                .build();
        log.info(result.toString());
        log.info(dto);
    }

    @Test
    public void modifyTest() {
        ApplierModifyDTO modifyDTO = new ApplierModifyDTO();
        modifyDTO.setAno(161L);
        modifyDTO.setStatus(2);

        ApplierEntity entity = applierEntityRepository.getApplier(modifyDTO.getAno()).get();

        if (modifyDTO.getStatus() == 1){
            entity.changeAccepted();
        }
        if (modifyDTO.getStatus() == 2){
            entity.changeRejected();
        }

        log.info(entity.toString());

    }
}
