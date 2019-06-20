package com.example.producer;

import com.example.common.domain.RequestParam;
import com.example.common.domain.RoutingKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jason Xiao
 */
@RestController
@Slf4j
public class DemoController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    private AsyncRabbitTemplate asyncRabbitTemplate;

//    @Autowired
//    private TaskExecutor taskExecutor;

    @PostMapping(value = "/send")
    public ResponseEntity send() {
        RequestParam req = new RequestParam(1L, "Jason Xiao");
        rabbitTemplate.convertAndSend(req);
//        for (long i = 0; i < 100; i++) {
//            RequestParam param = new RequestParam();
//            param.setId(i);
//            taskExecutor.execute(() -> {
//                log.info("Sending message: {}", param.toString());
//                Object response = rabbitTemplate.convertSendAndReceive(param);
//                log.info("Get result: {}", response);
//            });
//        }
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/send_and_receive")
    public ResponseEntity sendAndReceive() {
        RequestParam req = new RequestParam(1L, "Jason Xiao");
        Object response = rabbitTemplate.convertSendAndReceive(RoutingKey.RPC.name(), req);
        return ResponseEntity.ok(response);
    }

//    @PostMapping(value = "/async")
//    public ResponseEntity sendAsync(@RequestBody RequestParam param) throws ExecutionException, InterruptedException {
//        log.info("Sending message: {}", param.toString());
//        ListenableFuture<Object> responseFuture = asyncRabbitTemplate.convertSendAndReceive(param);
//        log.info("Waiting for result");
//        Object response = responseFuture.get();
//        log.info("Get result: {}", response);
//        return ResponseEntity.ok(response);
//    }


}
