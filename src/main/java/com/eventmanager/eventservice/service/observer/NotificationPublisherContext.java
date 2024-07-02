package com.eventmanager.eventservice.service.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationPublisherContext {

    public static final String FULL_NOTIFICATION = "full-notification";
//    TODO use for only in app notification when creating WebSocket Service
    public static final String IN_APP_NOTIFICATION = "in-app-notification";

    @Autowired
    public NotificationPublisherContext(NotificationPublisherService notificationPublisherService,
                                        EmailService emailService) {
        notificationPublisherService.subscribe(emailService);

        notificationPublisherService.subscribe(FULL_NOTIFICATION, emailService);
    }
}
