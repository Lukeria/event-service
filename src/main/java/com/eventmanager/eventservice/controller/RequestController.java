package com.eventmanager.eventservice.controller;

import com.eventmanager.eventservice.dto.RequestDtoRequest;
import com.eventmanager.eventservice.dto.RequestDtoResponse;
import com.eventmanager.eventservice.service.api.RequestAPIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления заявками.
 * Обрабатывает HTTP-запросы, связанные с заявками, такие как получение списка заявок,
 * получение конкретной заявки по ее идентификатору, создание, отклонение и удаление заявок.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
@RestController
@RequestMapping(path = "/api/v1/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestAPIService requestAPIService;

    /**
     * Получает список всех заявок.
     *
     * @return Список объектов типа RequestDtoResponse, содержащий информацию о заявках.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RequestDtoResponse> getRequestList() {
        return requestAPIService.getList();
    }

    /**
     * Получает заявку по ее идентификатору.
     *
     * @param id Идентификатор заявки.
     * @return Объект типа RequestDtoResponse, содержащий информацию о заявке.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RequestDtoResponse getById(@PathVariable Long id) {
        return requestAPIService.getById(id);
    }

    /**
     * Создает новую заявку на основе переданных данных.
     *
     * @param requestDtoRequest Объект типа RequestDtoRequest, содержащий данные для создания заявки.
     * @return Объект типа RequestDtoResponse, содержащий информацию о созданной заявке.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    public RequestDtoResponse save(@RequestBody @Valid RequestDtoRequest requestDtoRequest) {
        return requestAPIService.create(requestDtoRequest);
    }

    /**
     * Отклоняет заявку на основе переданных данных.
     *
     * @param requestDtoRequest Объект типа RequestDtoRequest, содержащий данные для отклонения заявки.
     * @return Объект типа RequestDtoResponse, содержащий информацию об отклоненной заявке.
     */
    @PutMapping("/decline")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ORGANIZER')")
    public RequestDtoResponse decline(@RequestBody RequestDtoRequest requestDtoRequest) {
        return requestAPIService.decline(requestDtoRequest);
    }

    /**
     * Удаляет заявку по ее идентификатору.
     *
     * @param id Идентификатор заявки, которую необходимо удалить.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public void delete(@PathVariable Long id) {
        requestAPIService.deleteById(id);
    }
}
