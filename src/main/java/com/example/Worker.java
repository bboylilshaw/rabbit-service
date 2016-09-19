package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author Jason Xiao
 */
@Component
public class Worker {

    private static final Logger logger = LoggerFactory.getLogger(Worker.class);

    @WorkerListener
    public Object onMessage(Message message) {
        logger.info("Received message: {}", message);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Sending response");
        return "response to " + message.getPayload().toString();
    }

}
