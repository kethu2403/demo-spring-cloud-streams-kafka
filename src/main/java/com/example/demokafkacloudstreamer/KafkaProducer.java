package com.example.demokafkacloudstreamer;

import com.example.demokafkacloudstreamer.order.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
public class KafkaProducer {
    
    @Autowired
    private StreamBridge streamBridge;

    public int count = 0;

    public void sendMessage(Order order) {
        streamBridge.send("producer-out-0", order, MimeTypeUtils.APPLICATION_JSON);
    }
}
