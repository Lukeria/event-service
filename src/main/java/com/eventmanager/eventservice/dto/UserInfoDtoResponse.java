package com.eventmanager.eventservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserInfoDtoResponse {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String roleName;
}
