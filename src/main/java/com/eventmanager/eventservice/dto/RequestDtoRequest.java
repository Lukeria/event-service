package com.eventmanager.eventservice.dto;

import com.eventmanager.eventservice.model.enums.RequestStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Объект передачи данных (DTO), представляющий запрос на создание заявки на мероприятия.
 * Содержит информацию о мероприятии, такую как его название, описание, дата, время, статус, место,
 * тип мероприятия, организатор и участник.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
@NoArgsConstructor
@Getter
@Setter
public class RequestDtoRequest {

    private Long id;

    /**
     * Название мероприятия.
     */
    @NotBlank(message = "Event name must not be blank")
    private String eventName;

    /**
     * Описание мероприятия.
     */
    @Size(max = 250, message = "Description is too long")
    private String eventDescription;

    /**
     * Дата мероприятия.
     */
    @NotNull(message = "Date must not be null")
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
    @NotNull(message = "Event type must not be null")
    private EventTypeDto type;

    /**
     * Информация об организаторе мероприятия.
     */
    @NotNull(message = "Organizer must be filled")
    private UserInfoDtoRequest organizer;

    /**
     * Информация об участнике мероприятия.
     */
    private UserInfoDtoRequest participant;
}
