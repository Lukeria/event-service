package com.eventmanager.eventservice.service.mapper;

import com.eventmanager.eventservice.dto.BudgetCategoryDtoRequest;
import com.eventmanager.eventservice.dto.BudgetCategoryDtoResponse;
import com.eventmanager.eventservice.model.BudgetCategory;
import org.mapstruct.Mapper;

@Mapper(uses = {PaymentMapper.class})
public interface BudgetCategoryMapper {

    BudgetCategoryDtoResponse mapToDto(BudgetCategory budgetCategory);
    BudgetCategory mapToModel(BudgetCategoryDtoRequest budgetCategoryDtoRequest);
}
