package com.eventmanager.eventservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class AuthDtoResponse {

    private String token;
    private UserInfoDtoResponse userInfo;
}
