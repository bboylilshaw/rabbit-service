package com.example.consumer;

import com.example.common.domain.RequestParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "#{T(com.example.common.domain.Queues).MULTIPLE_HANDLER}", ignoreDeclarationExceptions = "true"),
        exchange = @Exchange(value = "test.exchange", ignoreDeclarationExceptions = "true"),
        key = "test.multiple.handler"
))
public class MultipleHandler {

    @RabbitHandler
    public void handle(RequestParam message, @Headers Map<String, Object> headers) {
        log.info(message.toString());
        log.info(headers.toString());
    }

    @RabbitHandler
    public void handle(String message, @Headers Map<String, Object> headers) {
        log.info(message);
        log.info(headers.toString());
    }

}
