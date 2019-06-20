package com.example.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Scanner;

@Slf4j
@SpringBootApplication
public class RabbitProducerApplication implements CommandLineRunner {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static void main(String[] args) {
        log.warn("in main method");
        SpringApplication.run(RabbitProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.warn("within @Override run method");
        Scanner sc = null;
        try {
            sc = new Scanner(System.in);
            while (true) {
                String input = sc.nextLine();
                System.out.println(input);
                rabbitTemplate.convertAndSend(input);
            }
        } catch (Exception e) {
            log.error("Error", e);
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }

    static {
        log.warn("in static block");
    }

    @PostConstruct
    public void init() {
        log.warn("within @PostConstruct annotation");
    }
}
