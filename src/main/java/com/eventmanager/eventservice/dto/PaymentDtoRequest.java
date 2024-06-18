package com.eventmanager.eventservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaymentDtoRequest {

    private Long id;
    private String expenseName;
    private String description;
    private double amount;
    private Long budgetCategoryId;
}
