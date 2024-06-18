package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.model.Request;
import com.eventmanager.eventservice.service.api.RequestAPIService;

/**
 * Интерфейс сервиса для работы с запросами на мероприятия на уровне бизнес-логики.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
public interface RequestService extends RequestAPIService {

    /**
     * Получает модель запроса по его идентификатору.
     *
     * @param id Идентификатор запроса.
     * @return Модель запроса, соответствующая указанному идентификатору.
     */
    Request getModelById(Long id);
}
