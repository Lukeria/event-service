package com.eventmanager.eventservice.service.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationPublisherContext {

    @Autowired
    public NotificationPublisherContext(NotificationPublisherService notificationPublisherService,
                                        EmailService emailService) {
        notificationPublisherService.subscribe(emailService);
    }
}
