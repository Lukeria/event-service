package com.eventmanager.eventservice.controller;

import com.eventmanager.eventservice.dto.EventDtoRequest;
import com.eventmanager.eventservice.dto.EventDtoResponse;
import com.eventmanager.eventservice.dto.ParticipantInvitationDtoRequest;
import com.eventmanager.eventservice.service.api.EventAPIService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с мероприятиями.
 * Обрабатывает HTTP-запросы, связанные с мероприятиями.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
@RestController
@RequestMapping(path = "/api/v1/events")
public class EventController {

    private final EventAPIService eventService;

    public EventController(EventAPIService eventService) {
        this.eventService = eventService;
    }

    /**
     * Получает список всех мероприятий.
     *
     * @return Список объектов типа EventDtoResponse, содержащий информацию о мероприятиях.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventDtoResponse> getEventList() {
        return eventService.getList();
    }

    /**
     * Получает мероприятие по его UUID.
     *
     * @param uuid UUID мероприятия.
     * @return Объект типа EventDtoResponse, содержащий информацию о мероприятии.
     */
    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public EventDtoResponse getEventByUuid(@PathVariable("uuid") String uuid) {
        return eventService.getByUuid(uuid);
    }

    /**
     * Создает новое мероприятие.
     *
     * @param event Объект типа EventDtoRequest, содержащий данные для создания мероприятия.
     * @return Объект типа EventDtoResponse, содержащий информацию о созданном мероприятии.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ORGANIZER')")
    public EventDtoResponse save(@RequestBody @Valid EventDtoRequest event) {
        if (event.getRequestId() == null) {
            return eventService.create(event);
        } else {
            return eventService.createByRequest(event);
        }
    }

    /**
     * Обновляет информацию о мероприятии.
     *
     * @param event Объект типа EventDtoRequest, содержащий обновленные данные о мероприятии.
     * @return Объект типа EventDtoResponse, содержащий информацию об обновленном мероприятии.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public EventDtoResponse update(@RequestBody @Valid EventDtoRequest event) {
        return eventService.update(event);
    }

    /**
     * Добавляет пользователя к мероприятию.
     *
     * @param uuid UUID мероприятия.
     */
    @PutMapping("/{uuid}/users")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public void addUserToEvent(@PathVariable("uuid") String uuid,
                               @RequestBody String token) {
        eventService.addUserToEvent(uuid, token);
    }

    /**
     * Приглашает пользователя на мероприятие по его email.
     *
     * @param uuid                            UUID мероприятия.
     * @param participantInvitationDtoRequest Объект содержащий информацию с email приглашаемого участника и ссылкой
     *                                        для подтверждения приглашения.
     */
    @PutMapping("/{uuid}/invite")
    @ResponseStatus(HttpStatus.OK)
    public void inviteUserToEvent(@PathVariable("uuid") String uuid,
                                  @RequestBody @Valid ParticipantInvitationDtoRequest participantInvitationDtoRequest) {
        eventService.inviteUserToEvent(uuid, participantInvitationDtoRequest);
    }

    /**
     * Удаляет мероприятие по его идентификатору.
     *
     * @param id Идентификатор мероприятия, которое нужно удалить.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ORGANIZER')")
    public void delete(@PathVariable Long id) {
        eventService.deleteById(id);
    }
}
