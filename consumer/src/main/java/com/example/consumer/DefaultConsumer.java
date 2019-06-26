package com.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author Jason Xiao
 */
@Component
@Slf4j
public class DefaultConsumer {

    @RabbitListener(queues = "#{T(com.example.common.domain.Queues).DEFAULT}")
    public void onMessage(Message<Object> message) {
        log.info("Received message: {}", message);
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Finished consuming message: {}", message);
    }

}
