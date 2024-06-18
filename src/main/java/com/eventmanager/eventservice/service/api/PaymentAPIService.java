package com.eventmanager.eventservice.service.api;

import com.eventmanager.eventservice.dto.PaymentDtoRequest;
import com.eventmanager.eventservice.dto.PaymentDtoResponse;

public interface PaymentAPIService {
    PaymentDtoResponse create(String eventUuid, Long budgetCategoryId, PaymentDtoRequest paymentDtoRequest);

    PaymentDtoResponse update(String eventUuid, Long budgetCategoryId, PaymentDtoRequest paymentDtoRequest);

    void deleteById(String eventUuid, Long budgetCategoryId, Long paymentId);
}
