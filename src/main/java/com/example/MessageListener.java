package com.example;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jason Xiao
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
//@RabbitListener(
//        bindings = @QueueBinding(
//                value = @Queue(value = "test.queue", durable = "true", ignoreDeclarationExceptions = "true"),
//                exchange = @Exchange(value = "test.exchange", ignoreDeclarationExceptions = "true"),
//                key = "test.routing.key"
//        )
//)
public @interface MessageListener {
}
