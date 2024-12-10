package org.oz.association_boot.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.oz.association_boot.kafka.producer.KafkaCreatorProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final KafkaCreatorProducer kafkaCreatorProducer;

//    @KafkaListener(topics = "sample-creator")
//    public void AssociationConsumer(ConsumerRecord<String, String> record) {
//        log.info("CONSUME TEST====================================================");
//        log.info("Consumed message: " + record);
//
//        try {
//            // 메시지 값을 JSON으로 받음
//            String jsonMessage = record.value();
//
//            // ObjectMapper를 사용하여 JSON 문자열을 Java 객체로 변환
//            AuthProducerDTO producerDTO = objectMapper.readValue(jsonMessage, AuthProducerDTO.class);
//            log.info(producerDTO.toString());
//
//            Long ano = Long.parseLong(producerDTO.getAuthCode().substring(6));
//            log.info(ano);
//            ApplierEntity applierEntity = applierEntityRepository.getApplier(ano).get();
//
//            if (applierEntity.getAuthCheck()){
//                String jsonString = objectMapper.writeValueAsString("checked");
//                associationProducer.sendReceiveAuthCheck(jsonString);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }//end try
//    }

    @KafkaListener(topics = "sample-association")
    public void ProducerConsumer(ConsumerRecord<String, String> record) {
        log.info("CONSUME TEST====================================================");
        log.info("Consumed message: " + record);

        try {
            // 메시지 값을 JSON으로 받음
            String jsonMessage = record.value();

            // ObjectMapper를 사용하여 JSON 문자열을 Java 객체로 변환
//            AuthProducerDTO producerDTO = objectMapper.readValue(jsonMessage, AuthProducerDTO.class);

            log.info("JSON = " + jsonMessage);


        } catch (Exception e) {
            e.printStackTrace();
        }//end try
    }
}
