package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.dao.TaskRepository;
import com.eventmanager.eventservice.dto.TaskDtoRequest;
import com.eventmanager.eventservice.dto.TaskDtoResponse;
import com.eventmanager.eventservice.model.Checklist;
import com.eventmanager.eventservice.model.Task;
import com.eventmanager.eventservice.model.enums.TaskStatus;
import com.eventmanager.eventservice.service.api.TaskAPIService;
import com.eventmanager.eventservice.service.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskAPIService {

    private final TaskRepository taskRepository;
    private final ChecklistService checklistService;
    private final TaskMapper taskMapper;

    @Override
    public TaskDtoResponse create(String eventUuid, Long checklistId, TaskDtoRequest taskDtoRequest) {
        Checklist checklist = checklistService.getModelById(eventUuid, checklistId);

        Task task = taskMapper.mapToModel(taskDtoRequest);
        task.setId(null);
        task.setStatus(TaskStatus.PROGRESS);
        task.setChecklist(checklist);

        return taskMapper.mapToDto(taskRepository.save(task));
    }

    @Override
    public TaskDtoResponse update(String eventUuid, Long checklistId, TaskDtoRequest taskDtoRequest) {
        Task task = getModelById(checklistId, taskDtoRequest.getId());

        task.setName(taskDtoRequest.getName());
        task.setDescription(taskDtoRequest.getDescription());
        task.setDeadline(taskDtoRequest.getDeadline());

        return taskMapper.mapToDto(taskRepository.save(task));
    }

    @Override
    public TaskDtoResponse updateStatus(String eventUuid, Long checklistId, TaskDtoRequest taskDtoRequest) {
        Task task = getModelById(checklistId, taskDtoRequest.getId());

        if (taskDtoRequest.getStatus().equals(TaskStatus.DONE)) {
            task.setStatus(TaskStatus.PROGRESS);
        } else {
            task.setStatus(TaskStatus.DONE);
        }

        return taskMapper.mapToDto(taskRepository.save(task));
    }

    @Override
    public void deleteById(String eventUuid, Long checklistId, Long taskId) {
        taskRepository.deleteById(taskId);
    }

    private Task getModelById(Long checklistId, Long id) {
        return taskRepository.findByIdAndAndChecklistId(id, checklistId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Task with id " + id + " for checklist " + checklistId + " is not found"));
    }
}
