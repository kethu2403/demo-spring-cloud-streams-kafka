package com.example.demokafkacloudstreamer;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageProducer {
    
    int count = 0;

    @Bean
    public Supplier<Message> producer() {
        return() -> {
            count = count++;
            log.info("Produced: {}", "Message from supplier - " + count);
            return new Message("Message from supplier - " + count++);
        };
    }
}
