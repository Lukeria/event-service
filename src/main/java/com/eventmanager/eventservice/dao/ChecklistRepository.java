package com.eventmanager.eventservice.dao;

import com.eventmanager.eventservice.model.Checklist;
import com.eventmanager.eventservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

    List<Checklist> findAllByEvent(Event event);
    Optional<Checklist> findByIdAndEvent(Long id, Event event);
}
