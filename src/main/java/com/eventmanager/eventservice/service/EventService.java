package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.dto.EventDtoRequest;
import com.eventmanager.eventservice.model.Event;
import com.eventmanager.eventservice.service.api.EventAPIService;

/**
 * Интерфейс сервиса для работы с мероприятиями на уровне бизнес-логики.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
public interface EventService extends EventAPIService {

    /**
     * Получает модель мероприятия по его UUID.
     *
     * @param eventUuid UUID мероприятия.
     * @return Объект типа Event, содержащий модель мероприятия.
     */
    Event getModelByUuid(String eventUuid);

    /**
     * Получает модель мероприятия по его идентификатору.
     *
     * @param id Идентификатор мероприятия.
     * @return Объект типа Event, содержащий модель мероприятия.
     */
    Event getModelById(Long id);

    /**
     * Создает модель мероприятия на основе данных DTO.
     *
     * @param event Объект типа EventDtoRequest, содержащий данные о мероприятии.
     * @return Объект типа Event, содержащий созданную модель мероприятия.
     */
    Event createModel(EventDtoRequest event);

    /**
     * Получает модель мероприятия по его UUID бнз аутентифицированного пользователя
     *
     * @param eventUuid UUID мероприятия.
     * @return Объект типа Event, содержащий модель мероприятия.
     */
    Event getModelByUuidNotAuthenticated(String eventUuid);
}
