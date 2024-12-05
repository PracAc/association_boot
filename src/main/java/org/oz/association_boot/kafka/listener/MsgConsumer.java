package org.oz.association_boot.kafka.listener;


import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MsgConsumer {

    @KafkaListener(topics = "topic")
    public void listener(Object data) {


        log.info("2---------------------------------------------------");

        log.info("2---------------------------------------------------");

        log.info("2---------------------------------------------------");

        log.info("2---------------------------------------------------");

        log.info("2---------------------------------------------------");
        log.info("4444444444444---------------------------------------------------");

        System.out.println(data);
    }
}
