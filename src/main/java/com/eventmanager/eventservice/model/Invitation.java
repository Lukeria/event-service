package com.eventmanager.eventservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Модель сущности "Веб-приглашение".
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
@Entity
@Table(name = "invitations")
@NoArgsConstructor
@Getter
@Setter
public class Invitation {

    /**
     * Идентификатор приглашения.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Заголовок приглашения.
     */
    private String header;

    /**
     * Описание мероприятия.
     */
    @Column(name = "event_description")
    private String eventDescription;

    /**
     * Дата мероприятия.
     */
    @Column(name = "event_date")
    private String eventDate;

    /**
     * Время мероприятия.
     */
    @Column(name = "event_time")
    private String eventTime;

    /**
     * Место проведения мероприятия.
     */
    @Column(name = "event_place")
    private String eventPlace;

    /**
     * Адрес места проведения мероприятия.
     */
    @Column(name = "event_place_address")
    private String eventPlaceAddress;

    /**
     * URL изображения, связанного с мероприятием.
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * Сущность мероприятия, к которому относится приглашение.
     */
    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;

}
