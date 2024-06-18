package com.eventmanager.eventservice.controller;

import com.eventmanager.eventservice.dto.UserInfoDtoRequest;
import com.eventmanager.eventservice.dto.UserInfoDtoResponse;
import com.eventmanager.eventservice.service.api.UserInfoAPIService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class UserInfoController {

    private final UserInfoAPIService userInfoAPIService;

    public UserInfoController(UserInfoAPIService userInfoAPIService) {
        this.userInfoAPIService = userInfoAPIService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserInfoDtoResponse register(@RequestBody UserInfoDtoRequest userInfoDtoRequest) {
        return userInfoAPIService.createUser(userInfoDtoRequest);
    }

    @GetMapping("/organizers")
    @ResponseStatus(HttpStatus.OK)
    public List<UserInfoDtoResponse> getOrganizers() {
        return userInfoAPIService.getOrganizersList();
    }
}
