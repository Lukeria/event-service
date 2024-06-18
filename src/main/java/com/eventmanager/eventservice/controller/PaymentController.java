package com.eventmanager.eventservice.controller;

import com.eventmanager.eventservice.dto.PaymentDtoRequest;
import com.eventmanager.eventservice.dto.PaymentDtoResponse;
import com.eventmanager.eventservice.service.api.PaymentAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/events/{eventUuid}/budget-categories/{id}/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentAPIService paymentService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDtoResponse save(@PathVariable("eventUuid") String eventUuid,
                                   @PathVariable("id") Long budgetCategoryId,
                                   @RequestBody PaymentDtoRequest paymentDtoRequest) {
        return paymentService.create(eventUuid, budgetCategoryId, paymentDtoRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PaymentDtoResponse update(@PathVariable("eventUuid") String eventUuid,
                                     @PathVariable("id") Long budgetCategoryId,
                                     @RequestBody PaymentDtoRequest paymentDtoRequest) {
        return paymentService.update(eventUuid, budgetCategoryId, paymentDtoRequest);
    }

    @DeleteMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("eventUuid") String eventUuid,
                       @PathVariable("id") Long budgetCategoryId,
                       @PathVariable("paymentId") Long paymentId) {
        paymentService.deleteById(eventUuid, budgetCategoryId, paymentId);
    }
}
