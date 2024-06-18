package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.dao.EventRepository;
import com.eventmanager.eventservice.dto.EventDtoRequest;
import com.eventmanager.eventservice.dto.EventDtoResponse;
import com.eventmanager.eventservice.dto.ParticipantInvitationDtoRequest;
import com.eventmanager.eventservice.model.*;
import com.eventmanager.eventservice.resources.ApplicationProperties;
import com.eventmanager.eventservice.service.mapper.EventMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для управления мероприятиями.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventTypeService eventTypeService;
    private final EventMapper eventMapper;
    private final RequestService requestService;
    private final JavaMailSender emailSender;
    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final ApplicationProperties applicationProperties;
    private final StorageService storageService;
    private final JwtService jwtService;

    /**
     * Получает модель мероприятия по его UUID.
     *
     * @param eventUuid UUID мероприятия.
     * @return Объект типа Event, содержащий модель мероприятия.
     * @throws ResponseStatusException если мероприятие не найдено.
     */
    @Override
    public Event getModelByUuid(String eventUuid) {
        UserCredentials userCredentials = applicationUserDetailsService.getAuthenticatedUser();

        return eventRepository.findByUuidAndUserCredentialsListContains(eventUuid, userCredentials)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event is not found"));
    }

    /**
     * Получает модель мероприятия по его UUID бнз аутентифицированного пользователя
     *
     * @param eventUuid UUID мероприятия.
     * @return Объект типа Event, содержащий модель мероприятия.
     */
    @Override
    public Event getModelByUuidNotAuthenticated(String eventUuid) {
        return eventRepository.findByUuid(eventUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event is not found"));
    }

    /**
     * Получает модель мероприятия по его идентификатору.
     *
     * @param id Идентификатор мероприятия.
     * @return Объект типа Event, содержащий модель мероприятия.
     * @throws ResponseStatusException если мероприятие не найдено.
     */
    @Override
    public Event getModelById(Long id) {
        UserCredentials userCredentials = applicationUserDetailsService.getAuthenticatedUser();

        return eventRepository.findByIdAndUserCredentialsListContains(id, userCredentials)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event is not found"));
    }

    /**
     * Создает модель мероприятия на основе данных DTO.
     *
     * @param eventDtoRequest Объект типа EventDtoRequest, содержащий данные о мероприятии.
     * @return Объект типа Event, содержащий созданную модель мероприятия.
     */
    @Override
    public Event createModel(EventDtoRequest eventDtoRequest) {
        UserCredentials userCredentials = applicationUserDetailsService.getAuthenticatedUser();

        EventType eventType = eventTypeService.getById(eventDtoRequest.getType().getId());

        Event event = eventMapper.mapToModel(eventDtoRequest);
        event.setId(null);
        event.setType(eventType);
        event.setUuid(UuidUtil.generateUuid(12));

        Budget budget = new Budget();
        budget.setExpectedAmount(eventDtoRequest.getPlannedBudgetAmount());

        event.setBudget(budget);
        event.getUserCredentialsList().add(userCredentials);

        return eventRepository.save(event);
    }

    /**
     * Получает мероприятие по его UUID.
     *
     * @param eventUuid UUID мероприятия.
     * @return Объект типа EventDtoResponse, содержащий информацию о мероприятии.
     */
    @Override
    public EventDtoResponse getByUuid(String eventUuid) {
        Event event = getModelByUuid(eventUuid);
        return eventMapper.mapToDto(event);
    }

    /**
     * Получает список всех мероприятий.
     *
     * @return Список объектов типа EventDtoResponse, содержащий информацию о мероприятиях.
     */
    @Override
    public List<EventDtoResponse> getList() {
        UserCredentials userCredentials = applicationUserDetailsService.getAuthenticatedUser();

        return eventRepository.findAllByUserCredentialsListContains(userCredentials)
                .stream()
                .map(eventMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Создает новое мероприятие.
     *
     * @param eventDto Объект типа EventDtoRequest, содержащий данные для создания мероприятия.
     * @return Объект типа EventDtoResponse, содержащий информацию о созданном мероприятии.
     */
    @Override
    public EventDtoResponse create(EventDtoRequest eventDto) {
        Event createdEvent = createModel(eventDto);
        log.debug("Create event: " + createdEvent);
        return eventMapper.mapToDto(createdEvent);
    }

    /**
     * Обновляет информацию о мероприятии.
     *
     * @param eventDto Объект типа EventDtoRequest, содержащий обновленные данные о мероприятии.
     * @return Объект типа EventDtoResponse, содержащий информацию об обновленном мероприятии.
     */
    @Override
    public EventDtoResponse update(EventDtoRequest eventDto) {
        Event updatedEvent = getModelById(eventDto.getId());

        updatedEvent.setName(eventDto.getName());
        updatedEvent.setDescription(eventDto.getDescription());
        updatedEvent.setDate(eventDto.getDate());
        updatedEvent.setTime(eventDto.getTime());

        if (updatedEvent.getBudget() != null) {
            updatedEvent.getBudget().setExpectedAmount(eventDto.getPlannedBudgetAmount());
        }

        log.debug("Update event: " + updatedEvent);

        return eventMapper.mapToDto(eventRepository.save(updatedEvent));
    }

    /**
     * Удаляет мероприятие по его идентификатору.
     *
     * @param id Идентификатор мероприятия, которое нужно удалить.
     */
    @Override
    public void deleteById(Long id) {
        Event event = getModelById(id);
//        Invitation invitation = event.getInvitation();
//        String imageUrl = invitation.getImageUrl();
//        String filename = storageService.parseFileName(imageUrl);
//        if (filename != null) {
        storageService.deleteSubDirectory("event", event.getUuid());
//        }
        eventRepository.delete(event);
        log.debug("Delete event by id " + id);
    }

    /**
     * Создает новое мероприятие на основе запроса.
     *
     * @param eventDtoRequest Объект типа EventDtoRequest, содержащий данные для создания мероприятия.
     * @return Объект типа EventDtoResponse, содержащий информацию о созданном мероприятии.
     */
    @Override
    @Transactional
    public EventDtoResponse createByRequest(EventDtoRequest eventDtoRequest) {
        Long requestId = eventDtoRequest.getRequestId();
        Request request = requestService.getModelById(requestId);
        log.debug("Create event by request " + request);

        Event createdEvent = createModel(eventDtoRequest);

        createdEvent.getUserCredentialsList().clear();
        createdEvent.getUserCredentialsList().addAll(new ArrayList<>(request.getUserCredentialsList()));
        Event updatedEvent = eventRepository.save(createdEvent);

        requestService.deleteById(requestId);

        log.debug("Created event: " + updatedEvent);

        return eventMapper.mapToDto(updatedEvent);
    }

    /**
     * Добавляет пользователя к мероприятию.
     *
     * @param uuid  UUID мероприятия.
     * @param token токен для верификации пользователя, подтверждающего добавление мероприятия,
     *              которому была отправлена ссылка для подтверждения
     */
    @Override
    public void addUserToEvent(String uuid, String token) {
        UserCredentials userCredentials = applicationUserDetailsService.getAuthenticatedUser();
        Event eventModel = eventRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event is not found"));

        if (eventModel.getUserCredentialsList().contains(userCredentials)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Participant is already confirmed");
        }

        if (jwtService.validateConfirmationToken(token)) {
            eventModel.getUserCredentialsList().add(userCredentials);
            eventRepository.save(eventModel);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access to confirm participant is not allowed");
        }
    }

    /**
     * Приглашает пользователя на мероприятие по его invitationDtoRequest.
     *
     * @param uuid                 UUID мероприятия.
     * @param invitationDtoRequest Объект содержащий информацию с email приглашаемого участника и ссылкой
     *                             для подтверждения приглашения.
     */
    @Override
    public void inviteUserToEvent(String uuid, ParticipantInvitationDtoRequest invitationDtoRequest) {
        Event event = getModelByUuid(uuid);
//
//        if (!StringUtils.hasLength(invitationDtoRequest.getEmail())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is not filled");
//        }

        String confirmationToken = jwtService.generateConfirmationToken(invitationDtoRequest.getEmail());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(applicationProperties.getEmail());
        message.setTo(invitationDtoRequest.getEmail());
        message.setSubject("Invitation to participate in project");
        message.setText("Join our project!\n " +
                "Project details:\n" +
                "Name: " + event.getName() + "\n" +
                "Description: " + event.getDescription() + "\n" +
                "Press link to confirm participation: "
                + invitationDtoRequest.getParticipantConfirmationLink() + "/" + confirmationToken + "\n\n" +
                "If you don't want to participate in this project, ignore this message!");

        try {
            emailSender.send(message);
        } catch (MailException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
