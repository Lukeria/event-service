package com.eventmanager.eventservice.service.observer;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService extends NotificationSubscriberService{

    void sendMessage(SimpleMailMessage message);
}
