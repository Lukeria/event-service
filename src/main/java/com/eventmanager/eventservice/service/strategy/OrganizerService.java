package com.eventmanager.eventservice.service.strategy;

import com.eventmanager.eventservice.model.Organizer;

public interface OrganizerService extends UserInfoStrategy {

    Organizer getModelById(Long id);
}
