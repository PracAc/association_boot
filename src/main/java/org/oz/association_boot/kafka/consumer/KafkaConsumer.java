package org.oz.association_boot.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.oz.association_boot.kafka.dto.TestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class KafkaConsumer {
    @Autowired
    ObjectMapper objectMapper;

    @KafkaListener(topics = "sample-topic")
    public void consumeMessage(ConsumerRecord<String, String> record) {
        log.info("CONSUME TEST====================================================");
        log.info("Consumed message: " + record);


        try {
            // 메시지 값을 JSON으로 받음
            String jsonMessage = record.value();

            // ObjectMapper를 사용하여 JSON 문자열을 Java 객체로 변환
            TestDTO dto = objectMapper.readValue(jsonMessage, TestDTO.class);

            log.info(dto.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
