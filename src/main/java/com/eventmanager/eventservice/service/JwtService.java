package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.model.UserPrincipal;
import org.springframework.security.core.Authentication;

public interface JwtService{

    Authentication validateAuthToken(String token);
    String generateAuthToken(UserPrincipal userDetails);

    Boolean validateConfirmationToken(String token);
    String generateConfirmationToken(String email);
}
