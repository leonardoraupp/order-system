package com.leonardo.order_system.controller;

import com.leonardo.order_system.entities.Order;
import com.leonardo.order_system.service.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping
    public ResponseEntity<String> send(@RequestBody Order order) {
        orderProducer.sendOrder(order);
        return ResponseEntity.ok("Order received and sent to RabbitMQ!");
    }
}
