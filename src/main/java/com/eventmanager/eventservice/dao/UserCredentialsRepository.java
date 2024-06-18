package com.eventmanager.eventservice.dao;

import com.eventmanager.eventservice.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {

    Optional<UserCredentials> searchByLogin(String login);
}
