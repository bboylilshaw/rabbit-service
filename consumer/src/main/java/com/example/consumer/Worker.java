package com.example.consumer;

import com.example.common.domain.RequestParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author Jason Xiao
 */
@Component
@Slf4j
public class Worker {

    @RabbitListener(queues = "${spring.rabbitmq.listener.default.queue}")
    public void onMessage(Message<RequestParam> message) {
        log.info("Received message: {}", message);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        log.info("Sending response");
//        return "response to " + message.getPayload().toString();
    }

}
