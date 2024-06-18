package com.eventmanager.eventservice.controller;

import com.eventmanager.eventservice.dto.ChecklistDtoRequest;
import com.eventmanager.eventservice.dto.ChecklistDtoResponse;
import com.eventmanager.eventservice.dto.ChecklistProgressDtoResponse;
import com.eventmanager.eventservice.service.api.ChecklistAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/events/{eventUuid}/checklists")
@RequiredArgsConstructor
public class ChecklistController {

    private final ChecklistAPIService checklistService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ChecklistDtoResponse> getChecklistList(@PathVariable("eventUuid") String eventUuid){
        return checklistService.getList(eventUuid);
    }

    @GetMapping("/progress")
    @ResponseStatus(HttpStatus.OK)
    public List<ChecklistProgressDtoResponse> getChecklistProgressList(@PathVariable("eventUuid") String eventUuid){
        return checklistService.getChecklistProgressList(eventUuid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChecklistDtoResponse save(@PathVariable("eventUuid") String eventUuid,
                                          @RequestBody ChecklistDtoRequest checklistDtoRequest){
        return checklistService.create(eventUuid, checklistDtoRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ChecklistDtoResponse update(@PathVariable("eventUuid") String eventUuid,
                                            @RequestBody ChecklistDtoRequest checklistDtoRequest){
        return checklistService.update(eventUuid, checklistDtoRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("eventUuid") String eventUuid, @PathVariable Long id){
        checklistService.deleteById(eventUuid, id);
    }
}
