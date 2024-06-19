package com.eventmanager.eventservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class Notification {

    private Long id;
    private String subject;
    private String message;
    private LocalDate date;
    private List<UserCredentials> users;
}
