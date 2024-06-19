package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.dao.InvitationRepository;
import com.eventmanager.eventservice.dto.InvitationDtoRequest;
import com.eventmanager.eventservice.dto.InvitationDtoResponse;
import com.eventmanager.eventservice.dto.RVSPInfoDto;
import com.eventmanager.eventservice.model.Event;
import com.eventmanager.eventservice.model.Guest;
import com.eventmanager.eventservice.model.Invitation;
import com.eventmanager.eventservice.model.Notification;
import com.eventmanager.eventservice.resources.ApplicationProperties;
import com.eventmanager.eventservice.service.mapper.GuestMapper;
import com.eventmanager.eventservice.service.mapper.InvitationMapper;
import com.eventmanager.eventservice.service.observer.EmailService;
import com.eventmanager.eventservice.service.observer.NotificationPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final EventService eventService;
    private final GuestService guestService;
    private final InvitationMapper invitationMapper;
    private final GuestMapper guestMapper;
    private final ApplicationProperties applicationProperties;
    private final StorageService storageService;
    private final EmailService emailService;
    private final NotificationPublisherService notificationService;

    @Override
    public InvitationDtoResponse getInvitation(String eventUuid) {
        Event event = eventService.getModelByUuidNotAuthenticated(eventUuid);
        Invitation invitation = getModelByEvent(event);
        InvitationDtoResponse invitationDtoResponse = invitationMapper.mapToDto(invitation);
        invitationDtoResponse.setImageUrl(storageService.getImageSrcUrl(invitation.getImageUrl()));

        return invitationDtoResponse;
    }

    @Override
    public InvitationDtoResponse getInvitationForGuest(String eventUuid, String guestUuid) {
        Event event = eventService.getModelByUuidNotAuthenticated(eventUuid);

        Invitation invitation = getModelByEvent(event);

        Guest guest = guestService.getModelByUuid(guestUuid, event);
        InvitationDtoResponse invitationDtoResponse = invitationMapper.mapToDto(invitation);
        invitationDtoResponse.setGuest(guestMapper.mapToDto(guest));
        invitationDtoResponse.setImageUrl(storageService.getImageSrcUrl(invitation.getImageUrl()));

        return invitationDtoResponse;
    }

    @Override
    public InvitationDtoResponse confirm(String eventUuid, RVSPInfoDto rvspInfoDto) {
        Event event = eventService.getModelByUuidNotAuthenticated(eventUuid);
        Invitation invitation = getModelByEvent(event);

        Guest guest;
        if (rvspInfoDto.getGuestUuid() != null) {
            guest = guestService.getModelByUuid(rvspInfoDto.getGuestUuid(), event);
        } else {
            guest = guestService.getModelByNameAndSurname(rvspInfoDto.getName(), rvspInfoDto.getSurname(), event);
        }

        Guest updatedGuest = guestService.updateRVSPStatus(guest, rvspInfoDto.getRvspStatus());

        Notification notification = Notification.builder()
                .subject("RVSP confirmation")
                .message("Guest " + updatedGuest.getSurname() + " " + updatedGuest.getName() +
                        " change RVSP status: " + updatedGuest.getRvspStatus())
                .date(LocalDate.now())
                .users(invitation.getEvent().getUserCredentialsList())
                .build();

        notificationService.notifySubscribers(notification);

        InvitationDtoResponse invitationDtoResponse = invitationMapper.mapToDto(invitation);
        invitationDtoResponse.setGuest(guestMapper.mapToDto(updatedGuest));
        invitationDtoResponse.setImageUrl(storageService.getImageSrcUrl(invitation.getImageUrl()));

        return invitationDtoResponse;
    }

    @Override
    public InvitationDtoResponse create(String eventUuid, InvitationDtoRequest invitationDtoRequest) {
        Event event = eventService.getModelByUuid(eventUuid);

        Optional<Invitation> optionalInvitation = invitationRepository.getByEvent(event);
        if (optionalInvitation.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invitation already exists");
        }

        Invitation invitation = invitationMapper.mapToModel(invitationDtoRequest);
        invitation.setEvent(event);

        InvitationDtoResponse invitationDtoResponse = invitationMapper.mapToDto(invitationRepository.save(invitation));
        invitationDtoResponse.setImageUrl(storageService.getImageSrcUrl(invitation.getImageUrl()));

        return invitationDtoResponse;
    }

    @Override
    public InvitationDtoResponse update(String eventUuid, InvitationDtoRequest invitationDtoRequest) {
        Event event = eventService.getModelByUuid(eventUuid);
        Invitation invitation = getModelByEvent(event);

        invitation.setHeader(invitationDtoRequest.getHeader());
        invitation.setEventDescription(invitationDtoRequest.getEventDescription());
        invitation.setEventDate(invitationDtoRequest.getEventDate());
        invitation.setEventPlace(invitationDtoRequest.getEventPlace());
        invitation.setEventPlaceAddress(invitationDtoRequest.getEventPlaceAddress());
        invitation.setEventTime(invitation.getEventTime());

        InvitationDtoResponse invitationDtoResponse = invitationMapper.mapToDto(invitationRepository.save(invitation));
        invitationDtoResponse.setImageUrl(storageService.getImageSrcUrl(invitation.getImageUrl()));

        return invitationDtoResponse;
    }

    @Override
    public void deleteByEventUuid(String eventUuid) {
        Event event = eventService.getModelByUuid(eventUuid);
        Invitation invitation = getModelByEvent(event);

        invitationRepository.delete(invitation);
    }

    /**
     * Реализация метода для отправки приглашений на мероприятие.
     * Этот метод находит всех гостей для указанного мероприятия, фильтрует их по наличию email и отправляет им приглашения по email.
     *
     * @param eventUuid      UUID мероприятия.
     * @param invitationLink Ссылка для приглашения.
     * @throws ResponseStatusException если список email гостей пустой или если превышено количество попыток отправки email.
     */
    @Override
    public void sendInvitations(String eventUuid, String invitationLink) {
        // Получаем список гостей для указанного мероприятия
        List<Guest> guestList = guestService.getModelList(eventUuid);

        // Фильтруем гостей по наличию email
        List<Guest> filteredGuestList = guestList.stream()
                .filter(guest -> StringUtils.hasLength(guest.getEmail()))
                .toList();

        // Если список отфильтрованных гостей пуст, выбрасываем исключение с сообщением пользователю
        if (filteredGuestList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Guest emails list is empty");
        }

        // Для каждого гостя создаем и отправляем email сообщение
        for (Guest guest : filteredGuestList) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(applicationProperties.getEmail());
            message.setTo(guest.getEmail());
            message.setSubject("Invitation to join event");
            message.setText("Dear " + guest.getName() + " " + guest.getSurname() + "!\n" +
                    "We are pleased to invite you to our event!\n" +
                    "Press link to see details: "
                    + invitationLink + "/" + eventUuid);

            emailService.sendMessage(message);
        }
    }

    @Override
    public InvitationDtoResponse uploadFile(String eventUuid, MultipartFile file) {
        Event event = eventService.getModelByUuid(eventUuid);
        Invitation invitation = getModelByEvent(event);

        if (invitation.getImageUrl() != null) {
            String filename = storageService.parseFileName(invitation.getImageUrl());
            storageService.deleteFile(filename, "event", eventUuid);
        }

        String outputFilePath = storageService.storeFile(file, "event", eventUuid);
        invitation.setImageUrl(outputFilePath);

        InvitationDtoResponse invitationDtoResponse = invitationMapper.mapToDto(invitationRepository.save(invitation));
        invitationDtoResponse.setImageUrl(storageService.getImageSrcUrl(outputFilePath));

        return invitationDtoResponse;
    }

    @Override
    public Invitation getModelByEvent(Event event) {

        return invitationRepository.getByEvent(event)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invitation is not created yet"));
    }
}
