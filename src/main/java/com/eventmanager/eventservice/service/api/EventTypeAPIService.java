package com.eventmanager.eventservice.service.api;

import com.eventmanager.eventservice.dto.EventTypeDto;

import java.util.List;

public interface EventTypeAPIService {

    List<EventTypeDto> getList();
}
