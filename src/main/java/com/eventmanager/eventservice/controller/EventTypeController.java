package com.eventmanager.eventservice.controller;

import com.eventmanager.eventservice.dto.EventTypeDto;
import com.eventmanager.eventservice.service.api.EventTypeAPIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/event-types")
public class EventTypeController {

    private final EventTypeAPIService eventTypeService;

    public EventTypeController(EventTypeAPIService eventTypeService) {
        this.eventTypeService = eventTypeService;
    }

    @GetMapping
    public List<EventTypeDto> getEventTypeList() {
        return eventTypeService.getList();
    }
}
