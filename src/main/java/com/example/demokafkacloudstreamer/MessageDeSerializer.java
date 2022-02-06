package com.example.demokafkacloudstreamer;

import java.io.IOException;

import com.example.demokafkacloudstreamer.order.Order;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class MessageDeSerializer implements Deserializer<Order> {
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Order deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(new String(data), Order.class);
        } catch(IOException e) {
            throw new SerializationException(e);
        }
    }
}
