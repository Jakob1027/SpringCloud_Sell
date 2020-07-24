package com.jakob.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface StreamClient {

    String INPUT = "myMessage";

    @Input(INPUT)
    SubscribableChannel input();

    @Output(INPUT)
    MessageChannel output();
}
