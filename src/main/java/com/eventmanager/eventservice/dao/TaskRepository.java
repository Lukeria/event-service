package com.eventmanager.eventservice.dao;

import com.eventmanager.eventservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndAndChecklistId(Long id, Long checklistId);
}
