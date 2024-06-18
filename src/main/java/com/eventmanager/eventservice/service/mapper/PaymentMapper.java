package com.eventmanager.eventservice.service.mapper;

import com.eventmanager.eventservice.dto.PaymentDtoRequest;
import com.eventmanager.eventservice.dto.PaymentDtoResponse;
import com.eventmanager.eventservice.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PaymentMapper {

    @Mapping(source = "payment.budgetCategory.id", target = "budgetCategoryId")
    PaymentDtoResponse mapToDto(Payment payment);
    Payment mapToModel(PaymentDtoRequest paymentDtoRequest);
}
