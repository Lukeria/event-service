package com.eventmanager.eventservice.dao;

import com.eventmanager.eventservice.model.Event;
import com.eventmanager.eventservice.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    @Query("SELECT g FROM Guest g WHERE g.uuid = ?1 and g.event.id = ?2")
    Optional<Guest> findByUuidAndEvent(String uuid, Long eventId);

    @Query("SELECT g FROM Guest g WHERE g.event.id = ?1")
    List<Guest> findAllByEvent(Long eventId);

    @Query("SELECT g FROM Guest g WHERE g.id = ?1 and g.event.id = ?2")
    Optional<Guest> findByIdAndEvent(Long id, Long eventId);

    Optional<Guest> findByNameAndSurnameAndEvent(String name, String surname, Event event);
}
