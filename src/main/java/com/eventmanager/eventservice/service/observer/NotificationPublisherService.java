package com.eventmanager.eventservice.service.observer;

import com.eventmanager.eventservice.model.Notification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationPublisherService implements PublisherService<Notification> {

    private final List<NotificationSubscriberService> subscriberServiceList = new CopyOnWriteArrayList<>();

    @Override
    public void subscribe(SubscriberService<Notification> service) {
        if (!(service instanceof NotificationSubscriberService)) {
            throw new IllegalArgumentException("Service must be an instance of NotificationSubscriberService");
        }
        subscriberServiceList.add((NotificationSubscriberService) service);
    }

    @Override
    public void unsubscribe(SubscriberService<Notification> service) {
        if (!(service instanceof NotificationSubscriberService)) {
            throw new IllegalArgumentException("Service must be an instance of NotificationSubscriberService");
        }
        subscriberServiceList.remove((NotificationSubscriberService) service);
    }

    @Override
    public void notifySubscribers(Notification notification) {
        for (NotificationSubscriberService subscriberService : subscriberServiceList) {
            subscriberService.update(notification);
        }
    }
}
