package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.model.UserCredentials;
import com.eventmanager.eventservice.service.api.UserInfoAPIService;

import java.util.List;

public interface UserInfoService extends UserInfoAPIService {

    List<String> getEmailList(List<UserCredentials> userCredentialsList);
}
