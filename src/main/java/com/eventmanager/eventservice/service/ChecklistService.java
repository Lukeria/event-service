package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.model.Checklist;
import com.eventmanager.eventservice.service.api.ChecklistAPIService;

import java.util.List;

public interface ChecklistService extends ChecklistAPIService {

    Checklist getModelById(String eventUuid, Long id);
    List<Checklist> getModelList(String eventUuid);
}
