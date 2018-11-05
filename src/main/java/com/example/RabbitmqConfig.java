package com.example;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author Jason Xiao
 */
@EnableRabbit
@Configuration
public class RabbitmqConfig {

    @Value("${spring.rabbitmq.template.exchange}")
    private String EXCHANGE;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String ROUTING_KEY;

    @Value("${spring.rabbitmq.listener.default.queue}")
    public String QUEUE;

    @Value("${spring.rabbitmq.listener.default.reply.queue}")
    private String REPLY_QUEUE;

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        DirectExchange exchange = new DirectExchange(EXCHANGE, true, false);
        Queue queue = new Queue(QUEUE, true);
        Queue replyQueue = new Queue(REPLY_QUEUE, true);
        rabbitAdmin.setAutoStartup(true);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareQueue(replyQueue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY));
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
