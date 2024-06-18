package com.eventmanager.eventservice.service.mapper;

import com.eventmanager.eventservice.dto.UserInfoDtoRequest;
import com.eventmanager.eventservice.dto.UserInfoDtoResponse;
import com.eventmanager.eventservice.model.Organizer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrganizerMapper {

    @Mapping(source = "user.role.name", target = "roleName")
    UserInfoDtoResponse mapToDto(Organizer organizer);
    Organizer mapToModel(UserInfoDtoRequest userInfoDtoRequest);
}
