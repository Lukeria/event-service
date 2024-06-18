package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.model.UserCredentials;
import com.eventmanager.eventservice.service.api.AuthenticationAPIService;

public interface AuthenticationService extends AuthenticationAPIService {

    UserCredentials getAuthenticatedUser();
}
