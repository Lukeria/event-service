package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.dao.BudgetCategoryRepository;
import com.eventmanager.eventservice.dao.BudgetRepository;
import com.eventmanager.eventservice.dto.BudgetCategoryDtoRequest;
import com.eventmanager.eventservice.dto.BudgetCategoryDtoResponse;
import com.eventmanager.eventservice.dto.BudgetDtoResponse;
import com.eventmanager.eventservice.dto.PaymentDtoResponse;
import com.eventmanager.eventservice.model.Budget;
import com.eventmanager.eventservice.model.BudgetCategory;
import com.eventmanager.eventservice.model.Event;
import com.eventmanager.eventservice.service.mapper.BudgetCategoryMapper;
import com.eventmanager.eventservice.service.mapper.BudgetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BudgetCategoryServiceImpl implements BudgetCategoryService {

    private final BudgetCategoryRepository budgetCategoryRepository;
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;
    private final BudgetCategoryMapper budgetCategoryMapper;
    private final EventService eventService;

    @Override
    public BudgetDtoResponse getBudget(String eventUuid) {
        Budget budget = getBudgetModelByEventUuid(eventUuid);
        BudgetDtoResponse budgetDtoResponse = budgetMapper.mapToDto(budget);

        budgetDtoResponse.setPaidAmount(budgetDtoResponse.getBudgetCategoryList().stream()
                .mapToDouble(budgetCategoryDto -> {
                    if (budgetCategoryDto.getPaymentList() != null) {
                        double paidAmount =  getPaidAmountSum(budgetCategoryDto);
                        budgetCategoryDto.setPaidAmount(paidAmount);
                        return paidAmount;
                    } else {
                        return 0;
                    }
                })
                .sum());

        budgetDtoResponse.setPlannedAmount((budgetDtoResponse.getBudgetCategoryList().stream()
                .mapToDouble(BudgetCategoryDtoResponse::getPlannedAmount)
                .sum()));

        return budgetDtoResponse;
    }

    @Override
    public BudgetCategoryDtoResponse create(String eventUuid, BudgetCategoryDtoRequest budgetCategoryDtoRequest) {
        Budget budget = getBudgetModelByEventUuid(eventUuid);
        BudgetCategory budgetCategory = budgetCategoryMapper.mapToModel(budgetCategoryDtoRequest);
        budgetCategory.setId(null);
        budgetCategory.setBudget(budget);

        return budgetCategoryMapper.mapToDto(budgetCategoryRepository.save(budgetCategory));
    }

    @Override
    public BudgetCategoryDtoResponse update(String eventUuid, BudgetCategoryDtoRequest budgetCategoryDtoRequest) {
        BudgetCategory budgetCategory = getModelById(eventUuid, budgetCategoryDtoRequest.getId());

        budgetCategory.setName(budgetCategoryDtoRequest.getName());
        budgetCategory.setDescription(budgetCategoryDtoRequest.getDescription());
        budgetCategory.setPlannedAmount(budgetCategoryDtoRequest.getPlannedAmount());

        BudgetCategoryDtoResponse budgetCategoryDtoResponse = budgetCategoryMapper.mapToDto(
                budgetCategoryRepository.save(budgetCategory));
        budgetCategoryDtoResponse.setPaidAmount(getPaidAmountSum(budgetCategoryDtoResponse));

        return budgetCategoryDtoResponse;
    }

    @Override
    public void deleteById(String eventUuid, Long id) {
        budgetCategoryRepository.deleteById(id);
    }

    private Budget getBudgetModelByEventUuid(String eventUuid) {
        Event event = eventService.getModelByUuid(eventUuid);

        return budgetRepository.findByEvent(event)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Budget for event " + eventUuid + " is not found"));
    }

    private double getPaidAmountSum(BudgetCategoryDtoResponse budgetCategoryDtoResponse){
        return budgetCategoryDtoResponse.getPaymentList().stream()
                .mapToDouble(PaymentDtoResponse::getAmount)
                .sum();
    }

    @Override
    public BudgetCategory getModelById(String eventUuid, Long id) {
        Budget budget = getBudgetModelByEventUuid(eventUuid);
        return budgetCategoryRepository
                .findByIdAndBudget(id, budget)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Budget category " + id + " is not found"));
    }
}
