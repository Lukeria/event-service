package com.eventmanager.eventservice.dao;

import com.eventmanager.eventservice.model.Budget;
import com.eventmanager.eventservice.model.BudgetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Long> {

    Optional<BudgetCategory> findByIdAndBudget(Long id, Budget budget);
}
