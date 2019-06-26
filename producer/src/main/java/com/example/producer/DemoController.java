package com.example.producer;

import com.example.common.domain.RequestParam;
import com.example.common.domain.RoutingKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/send_and_receive")
    public ResponseEntity sendAndReceive() {
        RequestParam req = new RequestParam(1L, "Jason Xiao");
        Object response = rabbitTemplate.convertSendAndReceive(RoutingKey.RPC.name(), req);
        log.info("{}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/publish_confirm_and_return")
    public ResponseEntity publishConfirmAndReturn() {
        RequestParam req = new RequestParam(1L, "Jason Xiao");

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("confirm callback");
            System.out.println(correlationData);
            System.out.println(ack);
            System.out.println(cause);
        });

        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("return callback");
            System.out.println(message);
            System.out.println(replyCode);
            System.out.println(replyText);
            System.out.println(exchange);
            System.out.println(routingKey);
        });

        //exchange不存在，publish confirm ack为false，publish return无影响
        Object response1 = rabbitTemplate.convertSendAndReceive("non-exist-exchange", req, new CorrelationData("xx-1"));
        log.info("{}", response1);

        //unroutable message, publish return在ack之前返回message等信息
        Object response2 = rabbitTemplate.convertSendAndReceive(RoutingKey.RPC.name(), "non-exist-routing-key", req, new CorrelationData("xx-2"));
        log.info("{}", response2);

        //正常rpc，publish return无影响
        Object response3 = rabbitTemplate.convertSendAndReceive(RoutingKey.RPC.name(), req, new CorrelationData("xx-3"));
        log.info("{}", response3);

        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/multiple_handler")
    public ResponseEntity testMultipleHandler() {
        rabbitTemplate.convertAndSend("test.multiple.handler", new RequestParam(1L, "Jason Xiao"));
        rabbitTemplate.convertAndSend("test.multiple.handler", "Jason Xiao");
        return ResponseEntity.ok("OK");
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
