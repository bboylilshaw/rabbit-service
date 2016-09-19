package com.example;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author Jason Xiao
 */
@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(25);
        taskExecutor.setQueueCapacity(100);
        return taskExecutor;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactory containerFactory,
                                                                 TaskExecutor taskExecutor,
                                                                 MessageConverter messageConverter) {
        containerFactory.setTaskExecutor(taskExecutor);
        containerFactory.setMessageConverter(messageConverter);
        return containerFactory;
    }

}
