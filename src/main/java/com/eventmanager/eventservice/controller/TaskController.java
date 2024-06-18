package com.eventmanager.eventservice.controller;

import com.eventmanager.eventservice.dto.TaskDtoRequest;
import com.eventmanager.eventservice.dto.TaskDtoResponse;
import com.eventmanager.eventservice.service.api.TaskAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/events/{eventUuid}/checklists/{id}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskAPIService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDtoResponse save(@PathVariable("eventUuid") String eventUuid,
                                @PathVariable("id") Long checklistId,
                                @RequestBody TaskDtoRequest taskDtoRequest) {
        return taskService.create(eventUuid, checklistId, taskDtoRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public TaskDtoResponse update(@PathVariable("eventUuid") String eventUuid,
                                     @PathVariable("id") Long checklistId,
                                     @RequestBody TaskDtoRequest taskDtoRequest) {
        return taskService.update(eventUuid, checklistId, taskDtoRequest);
    }

    @PutMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public TaskDtoResponse updateStatus(@PathVariable("eventUuid") String eventUuid,
                                  @PathVariable("id") Long checklistId,
                                  @RequestBody TaskDtoRequest taskDtoRequest) {
        return taskService.updateStatus(eventUuid, checklistId, taskDtoRequest);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("eventUuid") String eventUuid,
                       @PathVariable("id") Long checklistId,
                       @PathVariable("taskId") Long taskId) {
        taskService.deleteById(eventUuid, checklistId, taskId);
    }
}
