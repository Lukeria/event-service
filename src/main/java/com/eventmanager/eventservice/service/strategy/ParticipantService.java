package com.eventmanager.eventservice.service.strategy;

import com.eventmanager.eventservice.model.Participant;
import com.eventmanager.eventservice.model.UserCredentials;

public interface ParticipantService extends UserInfoStrategy {

    Participant getModelByUser(UserCredentials userCredentials);

}
