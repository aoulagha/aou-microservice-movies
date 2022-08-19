package com.amin.dev.clients.notification;

public record NotificationRequest(
        Integer movieId,
        String movieName,
        String sender,
        String message
) {
}
