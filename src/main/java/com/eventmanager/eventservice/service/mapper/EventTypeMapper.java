package com.eventmanager.eventservice.service.mapper;

import com.eventmanager.eventservice.dto.EventTypeDto;
import com.eventmanager.eventservice.model.EventType;
import org.mapstruct.Mapper;

@Mapper
public interface EventTypeMapper {

    EventTypeDto mapToDto(EventType eventType);
    EventType mapToModel(EventTypeDto eventTypeDto);
}
