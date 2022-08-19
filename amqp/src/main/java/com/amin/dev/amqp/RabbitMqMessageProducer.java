package com.amin.dev.amqp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
public class RabbitMqMessageProducer {

    private final AmqpTemplate template;

    public void publish(Object payload, String exchange, String routineKey) {
        log.info("Publishing to Exchange : {} using routingKey : {}. With Payload {}", exchange, routineKey, payload);
        template.convertAndSend(exchange, routineKey, payload);
        log.info("Published to Exchange : {} using routingKey : {}. With Payload {}", exchange, routineKey, payload);
    }
}
