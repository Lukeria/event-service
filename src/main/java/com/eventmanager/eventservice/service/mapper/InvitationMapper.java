package com.eventmanager.eventservice.service.mapper;

import com.eventmanager.eventservice.dto.InvitationDtoRequest;
import com.eventmanager.eventservice.dto.InvitationDtoResponse;
import com.eventmanager.eventservice.model.Invitation;
import org.mapstruct.Mapper;

@Mapper
public interface InvitationMapper {

    InvitationDtoResponse mapToDto(Invitation invitation);

    Invitation mapToModel(InvitationDtoRequest request);
}
