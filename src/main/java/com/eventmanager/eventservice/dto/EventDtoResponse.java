package com.eventmanager.eventservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Объект передачи данных (DTO) для передачи данных о мероприятии от сервера к клиенту.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
@NoArgsConstructor
@Getter
@Setter
public class EventDtoResponse {

    /**
     * Уникальный идентификатор мероприятия.
     */
    private Long id;

    /**
     * Название мероприятия.
     */
    private String name;

    /**
     * Описание мероприятия.
     */
    private String description;

    /**
     * Дата проведения мероприятия.
     */
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
    private String place;

    /**
     * Тип мероприятия.
     */
    private EventTypeDto type;

    /**
     * Планируемая бюджетная сумма мероприятия.
     */
    private double plannedBudgetAmount;
}
