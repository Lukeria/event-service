package com.eventmanager.eventservice.service.mapper;

import com.eventmanager.eventservice.dto.ChecklistDtoRequest;
import com.eventmanager.eventservice.dto.ChecklistDtoResponse;
import com.eventmanager.eventservice.model.Checklist;
import org.mapstruct.Mapper;

@Mapper(uses = {TaskMapper.class})
public interface ChecklistMapper {

    ChecklistDtoResponse mapToDto(Checklist checklist);
    Checklist mapToModel(ChecklistDtoRequest checklistDtoRequest);
}
