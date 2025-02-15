package com.leonardo.order_system.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange("order-exchange", true, false);
    }

    @Bean
    public Queue orderQueue() {
        return QueueBuilder
                .durable("order-queue")
                .withArgument("x-dead-letter-exchange", "order-dlx")
                .withArgument("x-dead-letter-routing-key", "order-dlq")
                .build();
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(orderQueue).to(orderExchange).with("order-routing-key");
    }

    @Bean
    public DirectExchange orderDLX() {
        return new DirectExchange("order-dlx", true, false); // Turn on the exchange durable in case RabbitMQ has some instability.
    }

    @Bean
    public Queue orderDLQ() {
        return QueueBuilder.durable("order-dlq").build();
    }

    @Bean
    public Binding orderDLQBinding(Queue orderDLQ, DirectExchange orderDLX) {
        return BindingBuilder.bind(orderDLQ).to(orderDLX).with("order-dlq");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter); // Configura o conversor para usar JSON
        return rabbitTemplate;
    }
}
