package org.oz.association_boot.applier.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.oz.association_boot.applier.domain.ApplierEntity;
import org.oz.association_boot.applier.domain.ApplierStatus;
import org.oz.association_boot.applier.dto.ApplierListDTO;
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
                    .email("skwize@naver.com")
                    .zipcode("12345")
                    .roadAddr("Road Address" + i)
                    .lotNumAddr("Lot" + i)
                    .detailAddr("Detail Address" + i)
                    .addrEtc("Address ETC" + i)
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
                .zipcode(result.getZipcode())
                .roadAddr(result.getRoadAddr())
                .lotNumAddr(result.getLotNumAddr())
                .detailAddr(result.getDetailAddr())
                .addrEtc(result.getAddrEtc())
                .regStatus(result.getRegStatus())
                .regDate(result.getRegDate())
                .attachFileNames(fileNames)
                .build();
        log.info(result.toString());
        log.info(dto);
    }
}
