package com.eventmanager.eventservice.service.mapper;

import com.eventmanager.eventservice.dto.EventDtoRequest;
import com.eventmanager.eventservice.dto.EventDtoResponse;
import com.eventmanager.eventservice.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Интерфейс-маппер для преобразования между DTO и моделью мероприятия.*
 * Используется для передачи данных между уровнями приложения.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
@Mapper(uses = {EventTypeMapper.class})
public interface EventMapper {

    /**
     * Преобразует модель мероприятия в DTO мероприятия.
     *
     * @param event Объект типа Event, содержащий модель мероприятия.
     * @return Объект типа EventDtoResponse, содержащий DTO мероприятия.
     */
    @Mapping(source = "event.budget.expectedAmount", target = "plannedBudgetAmount")
    EventDtoResponse mapToDto(Event event);

    /**
     * Преобразует DTO мероприятия в модель мероприятия.
     *
     * @param eventDto Объект типа EventDtoRequest, содержащий DTO мероприятия.
     * @return Объект типа Event, содержащий модель мероприятия.
     */
    Event mapToModel(EventDtoRequest eventDto);
}
