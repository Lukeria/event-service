package com.eventmanager.eventservice.service.mapper;

import com.eventmanager.eventservice.dto.TaskDtoRequest;
import com.eventmanager.eventservice.dto.TaskDtoResponse;
import com.eventmanager.eventservice.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TaskMapper {

    @Mapping(source = "task.checklist.id", target = "checklistId")
    TaskDtoResponse mapToDto(Task task);
    Task mapToModel(TaskDtoRequest taskDtoRequest);
}
