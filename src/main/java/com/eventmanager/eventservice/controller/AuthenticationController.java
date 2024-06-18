package com.eventmanager.eventservice.controller;

import com.eventmanager.eventservice.dto.AuthDtoRequest;
import com.eventmanager.eventservice.dto.AuthDtoResponse;
import com.eventmanager.eventservice.service.api.AuthenticationAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationAPIService authenticationAPIService;

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthDtoResponse authenticate(@RequestBody AuthDtoRequest authDtoRequest) {
        AuthDtoResponse authDtoResponse = authenticationAPIService.authenticate(authDtoRequest);
        return authDtoResponse;
    }
}
