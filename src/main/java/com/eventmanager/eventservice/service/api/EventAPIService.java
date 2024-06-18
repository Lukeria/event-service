package com.eventmanager.eventservice.service.api;

import com.eventmanager.eventservice.dto.EventDtoRequest;
import com.eventmanager.eventservice.dto.EventDtoResponse;
import com.eventmanager.eventservice.dto.ParticipantInvitationDtoRequest;

import java.util.List;

/**
 * Интерфейс, определяющий методы для взаимодействия между уровнем представления и бизнес-логикой,
 * связанными с мероприятиями.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
public interface EventAPIService {

    /**
     * Получает мероприятие по его UUID.
     *
     * @param eventUuid UUID мероприятия.
     * @return Объект типа EventDtoResponse, содержащий информацию о мероприятии.
     */
    EventDtoResponse getByUuid(String eventUuid);

    /**
     * Получает список всех мероприятий.
     *
     * @return Список объектов типа EventDtoResponse, содержащий информацию о мероприятиях.
     */
    List<EventDtoResponse> getList();

    /**
     * Создает новое мероприятие.
     *
     * @param eventDto Объект типа EventDtoRequest, содержащий данные для создания мероприятия.
     * @return Объект типа EventDtoResponse, содержащий информацию о созданном мероприятии.
     */
    EventDtoResponse create(EventDtoRequest eventDto);

    /**
     * Обновляет информацию о мероприятии.
     *
     * @param eventDto Объект типа EventDtoRequest, содержащий обновленные данные о мероприятии.
     * @return Объект типа EventDtoResponse, содержащий информацию об обновленном мероприятии.
     */
    EventDtoResponse update(EventDtoRequest eventDto);

    /**
     * Удаляет мероприятие по его идентификатору.
     *
     * @param id Идентификатор мероприятия, которое нужно удалить.
     */
    void deleteById(Long id);

    /**
     * Создает новое мероприятие на основе запроса.
     *
     * @param event Объект типа EventDtoRequest, содержащий данные для создания мероприятия.
     * @return Объект типа EventDtoResponse, содержащий информацию о созданном мероприятии.
     */
    EventDtoResponse createByRequest(EventDtoRequest event);

    /**
     * Добавляет пользователя к мероприятию.
     *
     * @param uuid  UUID мероприятия.
     * @param token токен для верификации пользователя, подтверждающего добавление мероприятия,
     *              которому была отправлена ссылка для подтверждения
     */
    void addUserToEvent(String uuid, String token);

    /**
     * Приглашает пользователя присоединиться к управлению мероприятием по его email.
     *
     * @param uuid                 UUID мероприятия.
     * @param invitationDtoRequest Объект содержащий информацию с email приглашаемого участника и ссылкой
     *                             для подтверждения приглашения.
     */
    void inviteUserToEvent(String uuid, ParticipantInvitationDtoRequest invitationDtoRequest);
}
