package com.jakob.order.message;

import com.jakob.order.OrderApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MqreceiverTest extends OrderApplicationTests {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    void process() {
        amqpTemplate.convertAndSend("myQueue", "now: " + new Date());
    }

    @Test
    void sendFruit() {
        amqpTemplate.convertAndSend("myOrder", "fruit", "fruit now: " + new Date());
    }

    @Test
    void sendComputer() {
        amqpTemplate.convertAndSend("myOrder", "computer", "computer now: " + new Date());
    }
}