package com.amin.dev.clients.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "artist",
        url = "${clients.notif.url}"
)
public interface NotificationClient {

    @PostMapping(path = "/api/manager/notification")
    public void sendNotification(@RequestBody NotificationRequest notificationRequest);

}
