package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.model.BudgetCategory;
import com.eventmanager.eventservice.service.api.BudgetCategoryAPIService;

public interface BudgetCategoryService extends BudgetCategoryAPIService {

    BudgetCategory getModelById(String eventUuid, Long id);
}
