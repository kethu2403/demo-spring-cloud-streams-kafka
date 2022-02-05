package com.example.demokafkacloudstreamer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    
    @Autowired
    private StreamBridge streamBridge;

    public int count = 0;

    // @Scheduled(cron = "*/10 * * * * *")
    public void sendMessage() {
        streamBridge.send("producer-out-0", new Message("Message to be Streamed: " + count++));
    }
}
