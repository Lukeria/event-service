package com.eventmanager.eventservice.service.mapper;

import com.eventmanager.eventservice.dto.UserInfoDtoRequest;
import com.eventmanager.eventservice.dto.UserInfoDtoResponse;
import com.eventmanager.eventservice.model.Participant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ParticipantMapper {

    @Mapping(source = "user.role.name", target = "roleName")
    UserInfoDtoResponse mapToDto(Participant organizer);
    Participant mapToModel(UserInfoDtoRequest userInfoDtoRequest);
}
