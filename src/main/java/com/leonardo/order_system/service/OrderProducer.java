package com.leonardo.order_system.service;

import com.leonardo.order_system.entities.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrder(Order order) {
        rabbitTemplate.convertAndSend("order-exchange", "order-routing-key", order);
        log.info("Order sent to RabbitMQ: {}", order);
    }
}
