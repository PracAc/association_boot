
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
public class AssociationProducer {

    private final String TOPIC = "registry-creator"; // Kafka 토픽 이름
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(Object message) {
        log.error("Sending Message to producer", message);
        kafkaTemplate.send(TOPIC, message);
    }
}