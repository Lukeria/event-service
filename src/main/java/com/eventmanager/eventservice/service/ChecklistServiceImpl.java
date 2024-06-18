package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.dao.ChecklistRepository;
import com.eventmanager.eventservice.dto.ChecklistDtoRequest;
import com.eventmanager.eventservice.dto.ChecklistDtoResponse;
import com.eventmanager.eventservice.dto.ChecklistProgressDtoResponse;
import com.eventmanager.eventservice.model.Checklist;
import com.eventmanager.eventservice.model.Event;
import com.eventmanager.eventservice.model.Task;
import com.eventmanager.eventservice.model.enums.TaskStatus;
import com.eventmanager.eventservice.service.mapper.ChecklistMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChecklistServiceImpl implements ChecklistService {

    private final ChecklistMapper checklistMapper;
    private final ChecklistRepository checklistRepository;
    private final EventService eventService;

    @Override
    public ChecklistDtoResponse create(String eventUuid, ChecklistDtoRequest checklistDtoRequest) {
        Event event = eventService.getModelByUuid(eventUuid);

        Checklist checklist = checklistMapper.mapToModel(checklistDtoRequest);
        checklist.setId(null);
        checklist.setEvent(event);

        return checklistMapper.mapToDto(checklistRepository.save(checklist));
    }

    @Override
    public ChecklistDtoResponse update(String eventUuid, ChecklistDtoRequest checklistDtoRequest) {
        Checklist checklist = getModelById(eventUuid, checklistDtoRequest.getId());

        checklist.setName(checklistDtoRequest.getName());
        checklist.setDescription(checklistDtoRequest.getDescription());

        return checklistMapper.mapToDto(checklistRepository.save(checklist));
    }

    @Override
    public void deleteById(String eventUuid, Long id) {
        checklistRepository.deleteById(id);
    }

    @Override
    public List<ChecklistDtoResponse> getList(String eventUuid) {
        return getModelList(eventUuid).stream()
                .map(checklistMapper::mapToDto)
                .toList();
    }

    @Override
    public List<ChecklistProgressDtoResponse> getChecklistProgressList(String eventUuid) {
        List<Checklist> checklistList = getModelList(eventUuid);
        return checklistList.stream()
                .map(checklist -> {
                    List<Task> taskList = checklist.getTaskList();
                    return ChecklistProgressDtoResponse.builder()
                            .checklist(checklistMapper.mapToDto(checklist))
                            .valueMax(taskList.size())
                            .valueNow((int) taskList.stream()
                                    .filter(task -> task.getStatus() == TaskStatus.DONE)
                                    .count())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public Checklist getModelById(String eventUuid, Long id) {
        Event event = eventService.getModelByUuid(eventUuid);

        return checklistRepository.findByIdAndEvent(id, event)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Checklist with id " + id + " is not found"));
    }

    public List<Checklist> getModelList(String eventUuid) {
        Event event = eventService.getModelByUuid(eventUuid);

        return checklistRepository.findAllByEvent(event);
    }
}
