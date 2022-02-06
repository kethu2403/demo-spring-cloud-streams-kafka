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
        
        log.info("Placed Order: {}", orderIn);

        var order = Order.builder()//
                .itemName(orderIn.getItemName())//
                .orderUuid(orderIn.getOrderUuid())//
                .orderStatus(OrderStatus.PENDING)//
                .build(); 
        orderDataBase.put(order.getOrderUuid(), order);
        kafkaProducer.sendMessage("producer-out-0", order);
        return order;
    }

    public void checkInventory(Order orderIn) {
        log.info("Checking Inventory for Order: {}", orderIn);
        orderIn.setOrderStatus(OrderStatus.INVENTORY_CHECKING);
        orderDataBase.put(orderIn.getOrderUuid(), orderIn);

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(System.currentTimeMillis() % 2 == 0) {
            orderIn.setOrderStatus(OrderStatus.INSUFFICIENT_INVENTORY);
            orderDataBase.put(orderIn.getOrderUuid(), orderIn);
            log.warn("Just assuming order ran out of stock: {}", orderIn.getItemName());
            throw new OrderFailedExcepion(String.format("insufficient inventory for order: %s", orderIn.getOrderUuid()));
        }

        kafkaProducer.sendMessage("producer-out-1", orderIn);
    }

    public void shipTheOrder(Order orderIn) {
        log.info("Shipping Order: {}", orderIn);
        orderIn.setOrderStatus(OrderStatus.SHIPPED);
        orderDataBase.put(orderIn.getOrderUuid(), orderIn);
        log.info("OrderID: {} has been shipped", orderIn.getOrderUuid());
    }

    public void cancelTheOrder(Order orderIn) {
        log.info("Cancelling Order: {}", orderIn);
        orderIn.setOrderStatus(OrderStatus.CANCELED);
        orderDataBase.put(orderIn.getOrderUuid(), orderIn);
        log.info("OrderID: {} has been cancelled", orderIn.getOrderUuid());
    }

}
