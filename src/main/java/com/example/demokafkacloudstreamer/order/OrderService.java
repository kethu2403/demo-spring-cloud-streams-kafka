package com.example.demokafkacloudstreamer.order;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.example.demokafkacloudstreamer.KafkaProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    
    Map<UUID, Order> orderDataBase = new HashMap<>();

    @Autowired
    private KafkaProducer kafkaProducer;

    public Order placeOrder(Order orderIn) {
        log.debug("Placed Order orderIn: {}", orderIn);

        var order = Order.builder()//
                .itemName(orderIn.getItemName())//
                .orderUuid(UUID.randomUUID())//
                .orderStatus(OrderStatus.PENDING)//
                .build(); 
        orderDataBase.put(order.getOrderUuid(), order);
        kafkaProducer.sendMessage(order);
        return order;
    }

}
