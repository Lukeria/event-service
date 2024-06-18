package com.eventmanager.eventservice.service.api;

import com.eventmanager.eventservice.dto.BudgetCategoryDtoRequest;
import com.eventmanager.eventservice.dto.BudgetCategoryDtoResponse;
import com.eventmanager.eventservice.dto.BudgetDtoResponse;

public interface BudgetCategoryAPIService {
    BudgetDtoResponse getBudget(String eventUuid);

    BudgetCategoryDtoResponse create(String eventUuid, BudgetCategoryDtoRequest budgetCategoryDtoRequest);

    BudgetCategoryDtoResponse update(String eventUuid, BudgetCategoryDtoRequest budgetCategoryDtoRequest);

    void deleteById(String eventUuid, Long id);
}
