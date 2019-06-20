package com.example.consumer;

import com.example.common.domain.Queues;
import com.example.common.domain.RoutingKey;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jason Xiao
 */
@EnableRabbit
@Configuration
public class RabbitmqConfig {

    @Value("${spring.rabbitmq.template.exchange}")
    private String EXCHANGE;

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        DirectExchange exchange = new DirectExchange(EXCHANGE, true, false);
        Queue queue = new Queue(Queues.DEFAULT, true);
        Queue replyQueue = new Queue(Queues.DEFAULT_REPLY, true);
        rabbitAdmin.setAutoStartup(true);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareQueue(replyQueue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(RoutingKey.DEFAULT));

        Queue rpcQueue = new Queue(Queues.RPC, true, false, false);
        rabbitAdmin.declareQueue(rpcQueue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(rpcQueue).to(exchange).with(RoutingKey.RPC));

        rabbitAdmin.setIgnoreDeclarationExceptions(true);
        return rabbitAdmin;
    }

//    @Bean
//    public TaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(10);
//        taskExecutor.setMaxPoolSize(25);
//        taskExecutor.setQueueCapacity(100);
//        return taskExecutor;
//    }
//
//    @Bean
//    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactory containerFactory,
//                                                                 TaskExecutor taskExecutor,
//                                                                 MessageConverter messageConverter) {
//        containerFactory.setTaskExecutor(taskExecutor);
//        containerFactory.setMessageConverter(messageConverter);
//        return containerFactory;
//    }

}
