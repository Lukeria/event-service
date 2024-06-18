package com.eventmanager.eventservice.service.api;

import com.eventmanager.eventservice.dto.GuestDtoRequest;
import com.eventmanager.eventservice.dto.GuestDtoResponse;

import java.util.List;

public interface GuestAPIService {

    List<GuestDtoResponse> getList(String eventUuid);
    GuestDtoResponse getByUuid(String eventUuid, String uuid);
    GuestDtoResponse create(String eventUuid, GuestDtoRequest guestDto);
    GuestDtoResponse update(String eventUuid, GuestDtoRequest guestDto);
    void deleteById(String eventUuid, Long id);

}
