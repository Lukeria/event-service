package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.model.Event;
import com.eventmanager.eventservice.model.Invitation;
import com.eventmanager.eventservice.service.api.InvitationAPIService;

public interface InvitationService extends InvitationAPIService {

    Invitation getModelByEvent(Event event);
}
