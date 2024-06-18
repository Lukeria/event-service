package com.eventmanager.eventservice.dto;

import com.eventmanager.eventservice.model.enums.RVSPStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RVSPInfoDto {

    private String guestUuid;
    private String name;
    private String surname;
    private RVSPStatus rvspStatus;
}
