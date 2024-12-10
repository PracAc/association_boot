package org.oz.association_boot.kafka;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class KafkaTests {
//    @Autowired
//    private AssociationProducer producer;
//    @Autowired
//    private ApplierEntityRepository applierEntityRepository;

//    @Test
//    public void producerTest() throws JsonProcessingException {
//        TestDTO dto = TestDTO.builder()
//                .id(2L)
//                .name("TEST NAME")
//                .code("TEST CODE")
//                .build();
//
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = mapper.writeValueAsString(dto);
//
//        producer.sendMessage(jsonString);
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
//        TestDTO dto = TestDTO.builder()
//                .id(entity.getAno())
//                .name(entity.getName())
//                .code(entity.getRegStatus().toString())
//                .build();
//
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = mapper.writeValueAsString(dto);
//
//        producer.sendMessage(jsonString);
//
//        log.info(entity.toString());
//    }
}
