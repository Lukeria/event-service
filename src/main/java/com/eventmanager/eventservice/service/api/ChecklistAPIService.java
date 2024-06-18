package com.eventmanager.eventservice.service.api;

import com.eventmanager.eventservice.dto.ChecklistDtoRequest;
import com.eventmanager.eventservice.dto.ChecklistDtoResponse;
import com.eventmanager.eventservice.dto.ChecklistProgressDtoResponse;

import java.util.List;

public interface ChecklistAPIService {
    ChecklistDtoResponse create(String eventUuid, ChecklistDtoRequest checklistDtoRequest);

    ChecklistDtoResponse update(String eventUuid, ChecklistDtoRequest checklistDtoRequest);

    void deleteById(String eventUuid, Long id);

    List<ChecklistDtoResponse> getList(String eventUuid);

    List<ChecklistProgressDtoResponse> getChecklistProgressList(String eventUuid);
}
