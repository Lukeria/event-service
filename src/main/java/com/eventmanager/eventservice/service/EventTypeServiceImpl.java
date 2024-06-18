package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.dao.EventTypeRepository;
import com.eventmanager.eventservice.dto.EventTypeDto;
import com.eventmanager.eventservice.model.EventType;
import com.eventmanager.eventservice.service.EventTypeService;
import com.eventmanager.eventservice.service.mapper.EventTypeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventTypeServiceImpl implements EventTypeService {

    private final EventTypeRepository repository;
    private final EventTypeMapper mapper;

    public EventTypeServiceImpl(EventTypeRepository repository,
                                EventTypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public EventType getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event type is not found"));
    }

    @Override
    public List<EventTypeDto> getList() {
        return repository.findAll()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}
