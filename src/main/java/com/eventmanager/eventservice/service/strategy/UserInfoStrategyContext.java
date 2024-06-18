package com.eventmanager.eventservice.service.strategy;

import com.eventmanager.eventservice.model.enums.AuthorityName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserInfoStrategyContext {

    private final Map<String, UserInfoStrategy> strategyContext;

    @Autowired
    public UserInfoStrategyContext(ParticipantService participantService,
                                   OrganizerService organizerService) {
        strategyContext = new HashMap<>();
        strategyContext.put(AuthorityName.ROLE_ORGANIZER, organizerService);
        strategyContext.put(AuthorityName.ROLE_USER, participantService);
    }

    public UserInfoStrategy getStrategy(String key){
        if(!strategyContext.containsKey(key)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user role");
        }
        return strategyContext.get(key);
    }
}
