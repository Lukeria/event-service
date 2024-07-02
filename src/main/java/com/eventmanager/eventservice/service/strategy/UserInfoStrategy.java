package com.eventmanager.eventservice.service.strategy;

import com.eventmanager.eventservice.dto.UserInfoDtoRequest;
import com.eventmanager.eventservice.dto.UserInfoDtoResponse;
import com.eventmanager.eventservice.model.Authority;
import com.eventmanager.eventservice.model.UserCredentials;

import java.util.List;

public interface UserInfoStrategy {

    UserInfoDtoResponse getByUser(UserCredentials userCredentials);
    UserInfoDtoResponse createUser(UserInfoDtoRequest request, Authority authority);
    List<UserInfoDtoResponse> getList();
    List<String> getEmailList(List<UserCredentials> userCredentialsList);
}
