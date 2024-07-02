package com.eventmanager.eventservice.dao;

import com.eventmanager.eventservice.model.Organizer;
import com.eventmanager.eventservice.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

    Optional<Organizer> findByUser(UserCredentials userCredentials);
    List<Organizer> findByUserIn(List<UserCredentials> userCredentialsList);
}
