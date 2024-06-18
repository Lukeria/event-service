package com.eventmanager.eventservice.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Объект передачи данных (DTO) для передачи данных о мероприятии от клиента к серверу.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
@NoArgsConstructor
@Getter
@Setter
public class EventDtoRequest {

    /**
     * Уникальный идентификатор мероприятия.
     */
    private Long id;

    /**
     * Название мероприятия.
     */
    @NotBlank(message = "Name must not be blank")
    private String name;

    /**
     * Описание мероприятия.
     */
    @Size(max = 250, message = "Description is too long")
    private String description;

    /**
     * Дата проведения мероприятия.
     */
    @NotNull(message = "Date must not be null")
    private LocalDate date;

    /**
     * Время начала мероприятия.
     */
    private LocalTime time;

    /**
     * Уникальный идентификатор мероприятия в формате UUID.
     */
    private String uuid;

    /**
     * Место проведения мероприятия.
     */
    @NotBlank(message = "Place must not be blank")
    private String place;

    /**
     * Тип мероприятия.
     */
    @NotNull(message = "Event type must not be null")
    private EventTypeDto type;

    /**
     * Планируемая сумма бюджета мероприятия.
     */
    @Positive(message = "Budget amount is invalid")
    private double plannedBudgetAmount;

    /**
     * Идентификатор запроса, связанного с мероприятием.
     */
    private Long requestId;
}
