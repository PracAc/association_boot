//package org.oz.association_boot.kafka;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Test;
//import org.oz.association_boot.applier.domain.ApplierEntity;
//import org.oz.association_boot.applier.dto.ApplierModifyDTO;
//import org.oz.association_boot.applier.repository.ApplierEntityRepository;
//import org.oz.association_boot.kafka.dto.AuthProducerDTO;
//import org.oz.association_boot.kafka.producer.AssociationProducer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//@Log4j2
//public class KafkaTests {
//    @Autowired
//    AssociationProducer associationProducer;
//    @Autowired
//    ApplierEntityRepository applierEntityRepository;
//
//    @Test
//    public void producerTest() throws JsonProcessingException {
//        AuthProducerDTO dto = AuthProducerDTO.builder()
//                .creatorId("2L")
//                .creatorEmail("TEST NAME")
//                .creatorPassword("TEST CODE")
//                .build();
//
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = mapper.writeValueAsString(dto);
//
//        associationProducer.sendMessage(jsonString);
//    }
//
//    @Test
//    public void applierTest() throws JsonProcessingException {
//
//        ApplierModifyDTO modifyDTO = new ApplierModifyDTO();
//        modifyDTO.setAno(326L);
//        modifyDTO.setStatus(2);
//
//        ApplierEntity entity = applierEntityRepository.getApplier(modifyDTO.getAno()).get();
//
//        if (modifyDTO.getStatus() == 1){
//            entity.changeAccepted();
//        }
//        if (modifyDTO.getStatus() == 2){
//            entity.changeRejected();
//        }
//
//        applierEntityRepository.save(entity);
//
//        AuthProducerDTO dto = AuthProducerDTO.builder()
//                .creatorId(entity.getName())
//                .creatorName(entity.getName())
//                .creatorPassword(entity.getRegStatus().toString())
//                .build();
//
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = mapper.writeValueAsString(dto);
//
//        associationProducer.sendMessage(jsonString);
//
//        log.info(entity.toString());
//    }
//}
