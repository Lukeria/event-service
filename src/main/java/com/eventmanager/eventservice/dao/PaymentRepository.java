package com.eventmanager.eventservice.dao;

import com.eventmanager.eventservice.model.BudgetCategory;
import com.eventmanager.eventservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByIdAndAndBudgetCategory(Long id, BudgetCategory budgetCategory);
}
