package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.dao.GuestRepository;
import com.eventmanager.eventservice.dto.GuestDtoRequest;
import com.eventmanager.eventservice.dto.GuestDtoResponse;
import com.eventmanager.eventservice.model.Event;
import com.eventmanager.eventservice.model.Guest;
import com.eventmanager.eventservice.model.enums.RVSPStatus;
import com.eventmanager.eventservice.service.mapper.GuestMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final EventService eventService;
    private final GuestMapper guestMapper;

    public GuestServiceImpl(GuestRepository guestRepository,
                            EventService eventService,
                            GuestMapper guestMapper) {
        this.guestRepository = guestRepository;
        this.eventService = eventService;
        this.guestMapper = guestMapper;
    }

    @Override
    public List<GuestDtoResponse> getList(String eventUuid) {
        return getModelList(eventUuid)
                .stream()
                .map(guestMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GuestDtoResponse getByUuid(String eventUuid, String uuid) {

        Event event = eventService.getModelByUuid(eventUuid);
        Guest guest = getModelByUuid(uuid, event);
        return guestMapper.mapToDto(guest);
    }

    @Override
    public GuestDtoResponse create(String eventUuid, GuestDtoRequest guestDto) {

        Event event = eventService.getModelByUuid(eventUuid);

        Guest guest = guestMapper.mapToModel(guestDto);
        guest.setId(null);
        guest.setUuid(UuidUtil.generate32Uuid());
        guest.setEvent(event);

        if (guest.getRvspStatus() == null) {
            guest.setRvspStatus(RVSPStatus.UNDEFINED);
        }

        return guestMapper.mapToDto(guestRepository.save(guest));
    }

    @Override
    public GuestDtoResponse update(String eventUuid, GuestDtoRequest guestDto) {
        Guest guest = getById(eventUuid, guestDto.getId());

        guest.setName(guestDto.getName());
        guest.setSurname(guestDto.getSurname());
        guest.setGender(guestDto.getGender());
        guest.setEmail(guestDto.getEmail());

        return guestMapper.mapToDto(guestRepository.save(guest));
    }

    @Override
    public void deleteById(String eventUuid, Long id) {
        Guest guest = getById(eventUuid, id);
        guestRepository.deleteById(guest.getId());
    }

    @Override
    public Guest getById(String eventUuid, Long id) {
        Event event = eventService.getModelByUuid(eventUuid);

        return guestRepository.findByIdAndEvent(id, event.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest is not found"));
    }

    @Override
    public List<Guest> getModelList(String eventUuid) {
        Event event = eventService.getModelByUuid(eventUuid);
        return guestRepository.findAllByEvent(event.getId());
    }

    @Override
    public Guest getModelByUuid(String uuid, Event event) {
        return guestRepository.findByUuidAndEvent(uuid, event.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest is not found"));
    }

    @Override
    public Guest updateRVSPStatus(Guest guest, RVSPStatus rvspStatus) {
        guest.setRvspStatus(rvspStatus);
        return guestRepository.save(guest);
    }

    @Override
    public Guest getModelByNameAndSurname(String name, String surname, Event event) {
        return guestRepository.findByNameAndSurnameAndEvent(name, surname, event)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "There is no guest " + name + " " + surname + " for this event"));
    }
}
