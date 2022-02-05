package com.example.demokafkacloudstreamer;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class MessageDeSerializer implements Deserializer<Message> {
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Message deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(new String(data), Message.class);
        } catch(IOException e) {
            throw new SerializationException(e);
        }
    }
}
