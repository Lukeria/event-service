package com.eventmanager.eventservice.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "expense_name")
    private String expenseName;
    private String description;
    private double amount;
    @ManyToOne
    @JoinColumn(name = "budget_category_id", referencedColumnName = "id")
    private BudgetCategory budgetCategory;

}
