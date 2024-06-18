package com.eventmanager.eventservice.dao;

import com.eventmanager.eventservice.model.Budget;
import com.eventmanager.eventservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByEvent(Event event);
}
