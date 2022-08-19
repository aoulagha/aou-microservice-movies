package com.amin.dev.notification.rabbitmq;

import com.amin.dev.clients.notification.NotificationRequest;
import com.amin.dev.notification.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
public class RabbitConsumer {

    private final NotificationService service;

    @RabbitListener(queues = "${rabbitmq.queue.notification}")
    public void consumeNotification(NotificationRequest request) {
        log.info("Consume {} from " +
                "queue ", request);
        service.addNotification(request);
    }
}
