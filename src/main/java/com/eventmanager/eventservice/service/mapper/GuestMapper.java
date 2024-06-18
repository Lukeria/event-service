package com.eventmanager.eventservice.service.mapper;

import com.eventmanager.eventservice.dto.GuestDtoRequest;
import com.eventmanager.eventservice.dto.GuestDtoResponse;
import com.eventmanager.eventservice.model.Guest;
import org.mapstruct.Mapper;

@Mapper
public interface GuestMapper {

    GuestDtoResponse mapToDto(Guest guest);

    Guest mapToModel(GuestDtoRequest dto);
}
