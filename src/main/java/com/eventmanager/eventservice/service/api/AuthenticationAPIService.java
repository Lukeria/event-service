package com.eventmanager.eventservice.service.api;

import com.eventmanager.eventservice.dto.AuthDtoRequest;
import com.eventmanager.eventservice.dto.AuthDtoResponse;

public interface AuthenticationAPIService {

    AuthDtoResponse authenticate(AuthDtoRequest request);
}
