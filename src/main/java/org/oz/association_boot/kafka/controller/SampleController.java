package org.oz.association_boot.kafka.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.oz.association_boot.kafka.dto.AuthProducerDTO;
import org.oz.association_boot.kafka.producer.KafkaAssociationProducer;
import org.oz.association_boot.kafka.producer.KafkaCreatorProducer;
import org.oz.association_boot.kafka.producer.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class SampleController {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;
    // 제작자가 사용할 Producer
    private final KafkaCreatorProducer creatorProducer;
    // 협회가 사용할 Producer
    private final KafkaAssociationProducer associationProducer;

    @GetMapping("hello")
    public String[] sayHello() throws InterruptedException {

//        CompletableFuture<SendResult<String, Object>> result = kafkaTemplate.send("topic", "say hello : " + System.currentTimeMillis());

        log.info(kafkaTemplate.getDefaultTopic());

            String savedEntity = "겟을 실행해버렸음.";
            kafkaProducer.sendMessage(savedEntity);

            log.info("메세지 전송 완료");
        return new String[]{"AAA","BBB","CCC","DDD","EEE"};
    }

    @PostMapping("send")
    public String sendKafka(@RequestBody AuthProducerDTO producerDTO) {
        log.info("SEND KAFKA TEST=====================================");
        log.info(producerDTO);
        try {
            String jsonString = objectMapper.writeValueAsString(producerDTO);
            creatorProducer.sendAuthCheck(jsonString);
        }
        catch (Exception e){
        }

        return "tests";
    }
}
