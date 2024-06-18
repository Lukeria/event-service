package com.eventmanager.eventservice.service.mapper;

import com.eventmanager.eventservice.dto.BudgetDtoResponse;
import com.eventmanager.eventservice.model.Budget;
import org.mapstruct.Mapper;

@Mapper(uses = {BudgetCategoryMapper.class})
public interface BudgetMapper {

    BudgetDtoResponse mapToDto(Budget budget);
}
