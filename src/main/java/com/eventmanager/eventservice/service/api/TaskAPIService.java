package com.eventmanager.eventservice.service.api;

import com.eventmanager.eventservice.dto.TaskDtoRequest;
import com.eventmanager.eventservice.dto.TaskDtoResponse;

public interface TaskAPIService {
    TaskDtoResponse create(String eventUuid, Long checklistId, TaskDtoRequest taskDtoRequest);

    TaskDtoResponse update(String eventUuid, Long checklistId, TaskDtoRequest taskDtoRequest);

    TaskDtoResponse updateStatus(String eventUuid, Long checklistId, TaskDtoRequest taskDtoRequest);

    void deleteById(String eventUuid, Long checklistId, Long taskId);
}
