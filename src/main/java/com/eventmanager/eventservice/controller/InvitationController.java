package com.eventmanager.eventservice.controller;

import com.eventmanager.eventservice.dto.InvitationDtoRequest;
import com.eventmanager.eventservice.dto.InvitationDtoResponse;
import com.eventmanager.eventservice.dto.RVSPInfoDto;
import com.eventmanager.eventservice.service.api.InvitationAPIService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Контроллер для управления приглашениями на мероприятия.
 * Обрабатывает HTTP-запросы, связанные с приглашениями.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
@RestController
@RequestMapping(path = "/api/v1/events/{eventUuid}/invitation")
public class InvitationController {

    private final InvitationAPIService invitationService;

    public InvitationController(InvitationAPIService invitationService) {
        this.invitationService = invitationService;
    }

    /**
     * Получает приглашение на мероприятие.
     *
     * @param eventUuid UUID мероприятия.
     * @return Объект {@link InvitationDtoResponse}, содержащий данные о приглашении.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public InvitationDtoResponse getInvitation(@PathVariable("eventUuid") String eventUuid) {
        return invitationService.getInvitation(eventUuid);
    }

    /**
     * Получает приглашение на мероприятие для отображения всем пользователям и гостям
     *
     * @param eventUuid UUID мероприятия.
     * @param guestUuid (необязательно) UUID гостя для получения индивидуального приглашения.
     * @return Объект {@link InvitationDtoResponse}, содержащий данные о приглашении.
     */
    @GetMapping("/rvsp")
    @ResponseStatus(HttpStatus.OK)
    public InvitationDtoResponse getInvitation(@PathVariable("eventUuid") String eventUuid,
                                               @RequestParam(name = "guest", required = false) String guestUuid) {
        if (guestUuid == null) {
            return invitationService.getInvitation(eventUuid);
        } else {
            return invitationService.getInvitationForGuest(eventUuid, guestUuid);
        }
    }

    /**
     * Создает новое приглашение для мероприятия.
     *
     * @param eventUuid UUID мероприятия.
     * @param invitationDtoRequest DTO объекта запроса на создание приглашения.
     * @return Объект {@link InvitationDtoResponse}, содержащий данные о созданном приглашении.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvitationDtoResponse create(@PathVariable("eventUuid") String eventUuid,
                                        @RequestBody InvitationDtoRequest invitationDtoRequest) {
        return invitationService.create(eventUuid, invitationDtoRequest);
    }

    /**
     * Отправляет приглашение гостям.
     *
     * @param eventUuid UUID мероприятия.
     * @param invitationLink Ссылка на приглашение.
     */
    @PutMapping("/invite")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendInvitation(@PathVariable("eventUuid") String eventUuid, @RequestBody String invitationLink) {
        invitationService.sendInvitations(eventUuid, invitationLink);
    }

    /**
     * Обновляет приглашение для мероприятия.
     *
     * @param eventUuid UUID мероприятия.
     * @param invitationDtoRequest DTO объекта запроса на обновление приглашения.
     * @return Объект {@link InvitationDtoResponse}, содержащий данные об обновленном приглашении.
     */
    @PutMapping
    public InvitationDtoResponse update(@PathVariable("eventUuid") String eventUuid,
                                        @RequestBody InvitationDtoRequest invitationDtoRequest) {
        return invitationService.update(eventUuid, invitationDtoRequest);
    }

    /**
     * Подтверждает приглашение на мероприятие.
     *
     * @param eventUuid UUID мероприятия.
     * @param rvspInfoDto DTO объекта с информацией для подтверждения приглашения.
     * @return Объект {@link InvitationDtoResponse}, содержащий данные о подтвержденном приглашении.
     */
    @PutMapping("/confirm")
    @ResponseStatus(HttpStatus.OK)
    public InvitationDtoResponse confirmInvitation(@PathVariable("eventUuid") String eventUuid,
                                                   @RequestBody RVSPInfoDto rvspInfoDto) {
        return invitationService.confirm(eventUuid, rvspInfoDto);
    }

    /**
     * Загружает изображение для приглашения на мероприятие.
     *
     * @param eventUuid UUID мероприятия.
     * @param file Файл изображения для загрузки.
     * @return Объект {@link InvitationDtoResponse}, содержащий данные о приглашении с загруженным изображением.
     */
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public InvitationDtoResponse uploadImage(@PathVariable("eventUuid") String eventUuid,
                                             @RequestParam("file") MultipartFile file) {
        return invitationService.uploadFile(eventUuid, file);
    }
}
