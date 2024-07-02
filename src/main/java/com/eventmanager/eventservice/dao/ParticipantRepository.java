package com.eventmanager.eventservice.dao;

import com.eventmanager.eventservice.model.Participant;
import com.eventmanager.eventservice.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Optional<Participant> findByEmailAndUser(String email, UserCredentials userCredentials);
    Optional<Participant> findByUser(UserCredentials userCredentials);
    List<Participant> findByUserIn(List<UserCredentials> userCredentialsList);
}
