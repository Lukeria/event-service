package com.eventmanager.eventservice.dto;

import com.eventmanager.eventservice.model.enums.Gender;
import com.eventmanager.eventservice.model.enums.RVSPStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GuestDtoResponse {

    private Long id;
    private String name;
    private String surname;
    private Gender gender;
    private RVSPStatus rvspStatus;
    private String uuid;
    private String email;
}
