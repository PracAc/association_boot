package org.oz.association_boot.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class KafkaAssociationProducer {

    private final String TOPIC = "sample-association"; // Kafka 토픽 이름
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendReceiveAuthCheck(Object message) {
        log.error("Sending Message to producer", message);
        kafkaTemplate.send(TOPIC, message);
    }
}
