package com.eventmanager.eventservice.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class ApplicationProperties {

    @Value("${app.storage.location}")
    private String location;
    @Value("${spring.mail.username}")
    private String email;
    @Value("${app.jwt.secret}")
    private String secretKey;
    @Value("${app.invitation.send.attempts-count}")
    private Integer attemptsCount;

}
