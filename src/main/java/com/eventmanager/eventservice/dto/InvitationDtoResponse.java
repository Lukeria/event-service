package com.eventmanager.eventservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Объект передачи данных (DTO) для передачи данных о приглашении от сервера к клиенту.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
@NoArgsConstructor
@Getter
@Setter
public class InvitationDtoResponse {

    /**
     * Идентификатор приглашения.
     */
    private Long id;

    /**
     * Заголовок приглашения.
     */
    private String header;

    /**
     * Описание мероприятия.
     */
    private String eventDescription;

    /**
     * Дата мероприятия.
     */
    private String eventDate;

    /**
     * Время мероприятия.
     */
    private String eventTime;

    /**
     * Место проведения мероприятия.
     */
    private String eventPlace;

    /**
     * Адрес места проведения мероприятия.
     */
    private String eventPlaceAddress;

    /**
     * URL изображения, связанного с мероприятием.
     */
    private String imageUrl;

    /**
     * Информация о госте, получившем приглашение.
     */
    private GuestDtoResponse guest;
}
