package com.eventmanager.eventservice.dto;

import com.eventmanager.eventservice.model.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Объект передачи данных (DTO), представляющий ответ с информацией о событии.
 * Содержит детали, такие как название события, описание, дата, время, статус, место,
 * тип события, организатор и участник.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
@NoArgsConstructor
@Getter
@Setter
public class RequestDtoResponse {

    private Long id;

    /**
     * Название мероприятия.
     */
    private String eventName;

    /**
     * Описание мероприятия.
     */
    private String eventDescription;

    /**
     * Дата мероприятия.
     */
    private LocalDate eventDate;

    /**
     * Время мероприятия.
     */
    private LocalTime eventTime;

    /**
     * Статус запроса.
     */
    private RequestStatus status;

    /**
     * Место мероприятия.
     */
    private String eventPlace;

    /**
     * Тип мероприятия.
     */
    private EventTypeDto type;

    /**
     * Информация об организаторе мероприятия.
     */
    private UserInfoDtoResponse organizer;

    /**
     * Информация об участнике мероприятия.
     */
    private UserInfoDtoResponse participant;
}
