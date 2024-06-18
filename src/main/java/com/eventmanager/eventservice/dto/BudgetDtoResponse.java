package com.eventmanager.eventservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class BudgetDtoResponse {

    private Long id;
    private double expectedAmount;
    private double plannedAmount;
    private double paidAmount;
    private List<BudgetCategoryDtoResponse> budgetCategoryList;
}
