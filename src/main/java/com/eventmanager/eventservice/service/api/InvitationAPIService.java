package com.eventmanager.eventservice.service.api;

import com.eventmanager.eventservice.dto.InvitationDtoRequest;
import com.eventmanager.eventservice.dto.InvitationDtoResponse;
import com.eventmanager.eventservice.dto.RVSPInfoDto;
import org.springframework.web.multipart.MultipartFile;

public interface InvitationAPIService {
    InvitationDtoResponse getInvitation(String eventUuid);

    InvitationDtoResponse getInvitationForGuest(String eventUuid, String guestUuid);

    InvitationDtoResponse confirm(String eventUuid, RVSPInfoDto rvspInfoDto);

    InvitationDtoResponse create(String eventUuid, InvitationDtoRequest invitationDtoRequest);

    InvitationDtoResponse update(String eventUuid, InvitationDtoRequest invitationDtoRequest);

    void deleteByEventUuid(String eventUuid);

    void sendInvitations(String eventUuid, String invitationLink);

    InvitationDtoResponse uploadFile(String eventUuid, MultipartFile file);
}
