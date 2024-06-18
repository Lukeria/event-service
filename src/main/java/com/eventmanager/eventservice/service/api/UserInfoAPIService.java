package com.eventmanager.eventservice.service.api;

import com.eventmanager.eventservice.dto.UserInfoDtoRequest;
import com.eventmanager.eventservice.dto.UserInfoDtoResponse;
import com.eventmanager.eventservice.model.Organizer;
import com.eventmanager.eventservice.model.UserCredentials;

import java.util.List;

public interface UserInfoAPIService {

    UserInfoDtoResponse createUser(UserInfoDtoRequest request);
    List<UserInfoDtoResponse> getOrganizersList();
    UserInfoDtoResponse getByUserCredentials(UserCredentials userCredentials);
}
