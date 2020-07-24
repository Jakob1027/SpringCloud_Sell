package com.jakob.order.controller;


import com.jakob.order.dto.OrderDTO;
import com.jakob.order.message.StreamClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class StreamController {

    private final StreamClient streamClient;


    public StreamController(StreamClient streamClient) {
        this.streamClient = streamClient;
    }

    @GetMapping("/message")
    public void process() {
        streamClient.output().send(MessageBuilder.withPayload(new OrderDTO()).build());
    }

}
