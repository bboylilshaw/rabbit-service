package com.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RpcConsumer {

    @RabbitListener(queues = "#{T(com.example.common.domain.Queues).RPC}")
    public Message<Object> onMessage(Message<Object> message) {
        log.info("Received message: {}", message);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Sending rpc response");
        return message;
    }
}
