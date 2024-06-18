package com.eventmanager.eventservice.dto;

import com.eventmanager.eventservice.model.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GuestDtoRequest {

    private Long id;
    private String name;
    private String surname;
    private Gender gender;
    private String uuid;
    private String email;
}
