package com.eventmanager.eventservice.service.observer;

public interface PublisherService<T> {

    void subscribe(SubscriberService<T> service);
    void unsubscribe(SubscriberService<T> service);
    void notifySubscribers(T context);
}
