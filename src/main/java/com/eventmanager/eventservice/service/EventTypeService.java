package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.model.EventType;
import com.eventmanager.eventservice.service.api.EventTypeAPIService;

public interface EventTypeService extends EventTypeAPIService {

    EventType getById(Long id);
}
