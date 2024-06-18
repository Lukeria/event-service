package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.dao.RequestRepository;
import com.eventmanager.eventservice.dto.RequestDtoRequest;
import com.eventmanager.eventservice.dto.RequestDtoResponse;
import com.eventmanager.eventservice.model.*;
import com.eventmanager.eventservice.model.enums.RequestStatus;
import com.eventmanager.eventservice.service.mapper.RequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с запросами на мероприятия.
 *
 * @author Лукашевич Карина
 * @version 1.0
 * @since 2024-05-01
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final OrganizerService organizerService;
    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final ParticipantService participantService;
    private final RequestMapper mapper;

    /**
     * Получает список всех запросов на мероприятия.
     *
     * @return Список объектов типа RequestDtoResponse, содержащий информацию о запросах.
     */
    @Override
    public List<RequestDtoResponse> getList() {
        UserCredentials userCredentials = applicationUserDetailsService.getAuthenticatedUser();

        return requestRepository.findAllByUserCredentialsListContains(userCredentials).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Создает новый запрос на мероприятие.
     *
     * @param requestDtoRequest Объект типа RequestDtoRequest, содержащий данные для создания запроса.
     * @return Объект типа RequestDtoResponse, содержащий информацию о созданном запросе.
     * @throws ResponseStatusException если организатор не указан в запросе.
     */
    @Override
    public RequestDtoResponse create(RequestDtoRequest requestDtoRequest) {
        UserCredentials userCredentials = applicationUserDetailsService.getAuthenticatedUser();
        Participant participant = participantService.getModelByUser(userCredentials);

        Organizer organizer = organizerService.getModelById(requestDtoRequest.getOrganizer().getId());
        UserCredentials organizerUserCredentials = organizer.getUser();

        Request request = mapper.mapToModel(requestDtoRequest);
        request.setStatus(RequestStatus.CREATED);
        request.setOrganizer(organizer);
        request.setParticipant(participant);
        request.getUserCredentialsList().addAll(List.of(userCredentials, organizerUserCredentials));

        Request persistRequest = requestRepository.save(request);

        log.debug("Create request " + persistRequest);

        return mapper.mapToDto(persistRequest);
    }

    /**
     * Отклоняет запрос на мероприятие.
     *
     * @param requestDtoRequest Объект типа RequestDtoRequest, содержащий данные для отклонения запроса.
     * @return Объект типа RequestDtoResponse, содержащий информацию об отклоненном запросе.
     */
    @Override
    public RequestDtoResponse decline(RequestDtoRequest requestDtoRequest) {
        Request request = getModelById(requestDtoRequest.getId());
        request.setStatus(RequestStatus.DECLINED);

        Request persistRequest = requestRepository.save(request);

        log.debug("Create request " + persistRequest);

        return mapper.mapToDto(persistRequest);
    }

    /**
     * Удаляет запрос на мероприятие.
     *
     * @param id Идентификатор запроса, который необходимо удалить.
     */
    @Override
    public void deleteById(Long id) {
        log.debug("Delete request by id " + id);

        requestRepository.deleteById(id);
    }

    /**
     * Получает запрос на мероприятие по его идентификатору.
     *
     * @param id Идентификатор запроса.
     * @return Объект типа RequestDtoResponse, содержащий информацию о запросе.
     * @throws ResponseStatusException если запрос с указанным идентификатором не найден.
     */
    @Override
    public RequestDtoResponse getById(Long id) {
        log.debug("Get request by id " + id);

        return mapper.mapToDto(getModelById(id));
    }

    /**
     * Получает модель запроса на мероприятие по его идентификатору.
     *
     * @param id Идентификатор запроса.
     * @return Модель запроса на мероприятие, соответствующая указанному идентификатору.
     * @throws ResponseStatusException если запрос с указанным идентификатором не найден.
     */
    @Override
    public Request getModelById(Long id) {
        UserCredentials userCredentials = applicationUserDetailsService.getAuthenticatedUser();

        return requestRepository.findByIdAndUserCredentialsListContains(id, userCredentials)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Request is not found"));
    }
}
