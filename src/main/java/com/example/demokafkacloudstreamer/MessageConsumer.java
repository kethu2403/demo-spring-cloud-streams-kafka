package com.example.demokafkacloudstreamer;

import java.util.function.Consumer;

import com.example.demokafkacloudstreamer.order.Order;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageConsumer {

    @Bean
    public Consumer<Order> inventoryConsumer() {
        return message -> log.info("received: {}", message.toString());
    }
}
