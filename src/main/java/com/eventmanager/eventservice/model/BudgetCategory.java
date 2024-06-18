package com.eventmanager.eventservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "budget_categories")
@NoArgsConstructor
@Getter
@Setter
public class BudgetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(name = "planned_amount")
    private double plannedAmount;
    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;
    @OneToMany(mappedBy = "budgetCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> paymentList;
}
