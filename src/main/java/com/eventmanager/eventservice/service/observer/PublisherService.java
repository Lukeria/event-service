package com.eventmanager.eventservice.service.observer;

public interface PublisherService<T> {

    void subscribe(SubscriberService<T> service);
    void unsubscribe(SubscriberService<T> service);
    void notifySubscribers(T context);

    void subscribe(String topic, SubscriberService<T> service);
    void unsubscribe(String topic, SubscriberService<T> service);
    void notifySubscribers(String topic, T context);
}
