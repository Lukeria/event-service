package com.eventmanager.eventservice.controller;

import com.eventmanager.eventservice.dto.BudgetCategoryDtoRequest;
import com.eventmanager.eventservice.dto.BudgetCategoryDtoResponse;
import com.eventmanager.eventservice.dto.BudgetDtoResponse;
import com.eventmanager.eventservice.service.api.BudgetCategoryAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/events/{eventUuid}/budget-categories")
@RequiredArgsConstructor
public class BudgetCategoryController {

    private final BudgetCategoryAPIService budgetCategoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BudgetDtoResponse getBudget(@PathVariable("eventUuid") String eventUuid){
        return budgetCategoryService.getBudget(eventUuid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BudgetCategoryDtoResponse save(@PathVariable("eventUuid") String eventUuid,
                                          @RequestBody BudgetCategoryDtoRequest budgetCategoryDtoRequest){
        return budgetCategoryService.create(eventUuid, budgetCategoryDtoRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public BudgetCategoryDtoResponse update(@PathVariable("eventUuid") String eventUuid,
                                            @RequestBody BudgetCategoryDtoRequest budgetCategoryDtoRequest){
        return budgetCategoryService.update(eventUuid, budgetCategoryDtoRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("eventUuid") String eventUuid, @PathVariable Long id){
        budgetCategoryService.deleteById(eventUuid, id);
    }
}
