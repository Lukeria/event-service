package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.dto.UserInfoDtoRequest;
import com.eventmanager.eventservice.dto.UserInfoDtoResponse;
import com.eventmanager.eventservice.model.Authority;
import com.eventmanager.eventservice.model.Organizer;
import com.eventmanager.eventservice.model.UserCredentials;

import java.util.List;

public interface OrganizerService {

    Organizer getModelById(Long id);
    List<UserInfoDtoResponse> getList();
    UserInfoDtoResponse getByUser(UserCredentials userCredentials);
    UserInfoDtoResponse createUser(UserInfoDtoRequest request, Authority authority);
}
