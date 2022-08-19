package com.amin.dev.notification;

import com.amin.dev.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationService {
    private final NotificationRepository repository;

    public void addNotification(NotificationRequest request) {
        Notification notification = Notification.builder().message(request.message())
                .movieId(request.movieId())
                .sender(request.sender())
                .sentAt(LocalDateTime.now())
                .build();

        repository.saveAndFlush(notification);

        log.info(String.valueOf(notification.getNotificationId()));

    }
}
