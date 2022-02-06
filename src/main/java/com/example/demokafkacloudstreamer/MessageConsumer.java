package com.example.demokafkacloudstreamer;

import java.util.function.Consumer;

import com.example.demokafkacloudstreamer.order.Order;
import com.example.demokafkacloudstreamer.order.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @Autowired
    private OrderService orderService;

    @Bean
    public Consumer<Order> inventoryConsumer() {
        return message -> orderService.checkInventory(message);
    }

    @Bean
    public Consumer<Order> shippingConsumer() {
        return message -> orderService.shipTheOrder(message);
    }
    
    @Bean
    public Consumer<Order> orderDlqConsumer() {
        return message -> orderService.cancelTheOrder(message);
    }
}
