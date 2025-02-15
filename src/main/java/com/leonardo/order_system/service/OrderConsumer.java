package com.leonardo.order_system.service;

import com.leonardo.order_system.entities.Order;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class OrderConsumer {

    @RabbitListener(queues = "order-queue")
    public void receiveOrder(Order order, Message message, Channel channel) throws IOException, InterruptedException {
        if (order.getAmount() <= 0) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            log.error("Invalid order reject and sent to DLQ: {}", order);
        } else {
            log.info("Successfully processed valid order: {}", order);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
