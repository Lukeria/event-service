package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.model.Event;
import com.eventmanager.eventservice.model.Guest;
import com.eventmanager.eventservice.model.enums.RVSPStatus;
import com.eventmanager.eventservice.service.api.GuestAPIService;

import java.util.List;

public interface GuestService extends GuestAPIService {

    Guest getModelByUuid(String uuid, Event event);
    Guest updateRVSPStatus(Guest guest, RVSPStatus rvspStatus);
    Guest getModelByNameAndSurname(String name, String surname, Event event);
    Guest getById(String eventUuid, Long id);
    List<Guest> getModelList(String eventUuid);
}
