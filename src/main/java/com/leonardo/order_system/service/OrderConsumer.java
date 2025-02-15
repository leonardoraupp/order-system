package com.leonardo.order_system.service;

import com.leonardo.order_system.entities.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderConsumer {

    @RabbitListener(queues = "order-queue")
    public void receiveOrder(Order order) throws IllegalAccessException {
        if (order.getAmount() < 0) {
            log.warn("Invalid order received.", order);
            throw new IllegalAccessException("Invalid order amount.");
        } else {
            log.info("Processing valid order: {}", order);
        }
    }
}
