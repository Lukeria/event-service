package com.eventmanager.eventservice.controller;

import com.eventmanager.eventservice.dto.GuestDtoRequest;
import com.eventmanager.eventservice.dto.GuestDtoResponse;
import com.eventmanager.eventservice.service.api.GuestAPIService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/events/{eventUuid}/guests")
public class GuestController {

    private final GuestAPIService guestService;

    public GuestController(GuestAPIService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GuestDtoResponse> getGuestList(@PathVariable("eventUuid") String eventUuid){
        return guestService.getList(eventUuid);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public GuestDtoResponse getGuestByUuid(@PathVariable("eventUuid") String eventUuid, @PathVariable("uuid") String uuid){
        return guestService.getByUuid(eventUuid, uuid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GuestDtoResponse save(@PathVariable("eventUuid") String eventUuid, @RequestBody GuestDtoRequest guest){
        return guestService.create(eventUuid, guest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public GuestDtoResponse update(@PathVariable("eventUuid") String eventUuid, @RequestBody GuestDtoRequest guest){
        return guestService.update(eventUuid, guest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("eventUuid") String eventUuid, @PathVariable Long id){
        guestService.deleteById(eventUuid, id);
    }
}
