package com.eventmanager.eventservice.service.observer;

public interface SubscriberService<T> {

    void update(T context);
}
