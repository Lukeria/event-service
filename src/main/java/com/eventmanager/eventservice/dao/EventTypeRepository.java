package com.eventmanager.eventservice.dao;

import com.eventmanager.eventservice.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {
}
