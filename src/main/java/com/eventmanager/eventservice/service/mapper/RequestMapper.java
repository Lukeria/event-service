package com.eventmanager.eventservice.service.mapper;

import com.eventmanager.eventservice.dto.RequestDtoRequest;
import com.eventmanager.eventservice.dto.RequestDtoResponse;
import com.eventmanager.eventservice.model.Request;
import org.mapstruct.Mapper;

/**
 * Маппер, отвечающий за преобразование между объектами запросов и их DTO.
 * Используется для передачи данных между уровнями приложения.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
@Mapper(uses = {OrganizerMapper.class, EventTypeMapper.class, ParticipantMapper.class})
public interface RequestMapper {

    /**
     * Преобразует объект Request в объект RequestDtoResponse.
     *
     * @param request Объект Request для преобразования.
     * @return Преобразованный объект RequestDtoResponse.
     */
    RequestDtoResponse mapToDto(Request request);

    /**
     * Преобразует объект RequestDtoRequest в объект Request.
     *
     * @param requestDtoRequest Объект RequestDtoRequest для преобразования.
     * @return Преобразованный объект Request.
     */
    Request mapToModel(RequestDtoRequest requestDtoRequest);
}
