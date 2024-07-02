package com.eventmanager.eventservice.service.observer;

import com.eventmanager.eventservice.model.Notification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationPublisherService implements PublisherService<Notification> {

    private final List<NotificationSubscriberService> subscriberServiceList = new CopyOnWriteArrayList<>();
    private final Map<String, List<NotificationSubscriberService>> subscriberServiceMap = new ConcurrentHashMap<>();

    @Override
    public void subscribe(SubscriberService<Notification> service) {
        checkServiceType(service);
        subscriberServiceList.add((NotificationSubscriberService) service);
    }

    @Override
    public void unsubscribe(SubscriberService<Notification> service) {
        checkServiceType(service);
        subscriberServiceList.remove((NotificationSubscriberService) service);
    }

    @Override
    public void notifySubscribers(Notification notification) {
        for (NotificationSubscriberService subscriberService : subscriberServiceList) {
            subscriberService.update(notification);
        }
    }

    @Override
    public void subscribe(String topic, SubscriberService<Notification> service) {
        checkServiceType(service);
        List<NotificationSubscriberService> subscriberServiceList;
        if (subscriberServiceMap.containsKey(topic)) {
            subscriberServiceList = subscriberServiceMap.get(topic);
        } else {
            subscriberServiceList = new CopyOnWriteArrayList<>();
        }
        subscriberServiceList.add((NotificationSubscriberService) service);
        subscriberServiceMap.put(topic, subscriberServiceList);
    }

    @Override
    public void unsubscribe(String topic, SubscriberService<Notification> service) {
        checkServiceType(service);
        List<NotificationSubscriberService> subscriberServiceList;
        if (subscriberServiceMap.containsKey(topic)) {
            subscriberServiceList = subscriberServiceMap.get(topic);
        } else {
            subscriberServiceList = new CopyOnWriteArrayList<>();

        }
        subscriberServiceList.remove((NotificationSubscriberService) service);
    }

    @Override
    public void notifySubscribers(String topic, Notification notification) {
        if (subscriberServiceMap.containsKey(topic)) {
            for (NotificationSubscriberService subscriberService : subscriberServiceMap.get(topic)) {
                subscriberService.update(notification);
            }
        }
    }

    private void checkServiceType(SubscriberService<Notification> service) {
        if (!(service instanceof NotificationSubscriberService)) {
            throw new IllegalArgumentException("Service must be an instance of NotificationSubscriberService");
        }
    }
}
