package com.eventmanager.eventservice.dao;

import com.eventmanager.eventservice.model.Event;
import com.eventmanager.eventservice.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> getByEvent(Event event);

}
