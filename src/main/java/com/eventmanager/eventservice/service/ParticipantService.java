package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.dto.UserInfoDtoRequest;
import com.eventmanager.eventservice.dto.UserInfoDtoResponse;
import com.eventmanager.eventservice.model.Authority;
import com.eventmanager.eventservice.model.Participant;
import com.eventmanager.eventservice.model.UserCredentials;

public interface ParticipantService {

    Participant getModelByUser(UserCredentials userCredentials);
    UserInfoDtoResponse getByUser(UserCredentials userCredentials);
    UserInfoDtoResponse createUser(UserInfoDtoRequest request, Authority authority);
}
