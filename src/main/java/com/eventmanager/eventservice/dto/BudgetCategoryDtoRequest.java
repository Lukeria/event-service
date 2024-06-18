package com.eventmanager.eventservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class BudgetCategoryDtoRequest {

    private Long id;
    private String name;
    private String description;
    private double plannedAmount;
    private List<PaymentDtoRequest> paymentList;
}
