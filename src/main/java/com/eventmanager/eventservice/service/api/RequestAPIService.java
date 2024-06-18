package com.eventmanager.eventservice.service.api;

import com.eventmanager.eventservice.dto.RequestDtoRequest;
import com.eventmanager.eventservice.dto.RequestDtoResponse;

import java.util.List;

/**
 * Интерфейс, определяющий методы для взаимодействия между уровнем представления и бизнес-логикой,
 * связанными с запросами на мероприятия.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
public interface RequestAPIService {

    /**
     * Получает список всех запросов.
     *
     * @return Список объектов типа RequestDtoResponse, содержащий информацию о запросах.
     */
    List<RequestDtoResponse> getList();

    /**
     * Создает новый запрос на основе переданных данных.
     *
     * @param requestDtoRequest Объект типа RequestDtoRequest, содержащий данные для создания запроса.
     * @return Объект типа RequestDtoResponse, содержащий информацию о созданном запросе.
     */
    RequestDtoResponse create(RequestDtoRequest requestDtoRequest);

    /**
     * Отклоняет запрос на основе переданных данных.
     *
     * @param requestDtoRequest Объект типа RequestDtoRequest, содержащий данные для отклонения запроса.
     * @return Объект типа RequestDtoResponse, содержащий информацию об отклоненном запросе.
     */
    RequestDtoResponse decline(RequestDtoRequest requestDtoRequest);

    /**
     * Удаляет запрос по его идентификатору.
     *
     * @param id Идентификатор запроса, который необходимо удалить.
     */
    void deleteById(Long id);

    /**
     * Получает запрос по его идентификатору.
     *
     * @param id Идентификатор запроса.
     * @return Объект типа RequestDtoResponse, содержащий информацию о запросе.
     */
    RequestDtoResponse getById(Long id);
}
