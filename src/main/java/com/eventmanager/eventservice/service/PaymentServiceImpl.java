package com.eventmanager.eventservice.service;

import com.eventmanager.eventservice.dao.PaymentRepository;
import com.eventmanager.eventservice.dto.PaymentDtoRequest;
import com.eventmanager.eventservice.dto.PaymentDtoResponse;
import com.eventmanager.eventservice.model.BudgetCategory;
import com.eventmanager.eventservice.model.Payment;
import com.eventmanager.eventservice.service.api.PaymentAPIService;
import com.eventmanager.eventservice.service.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentAPIService {

    private final PaymentRepository paymentRepository;
    private final BudgetCategoryService budgetCategoryService;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentDtoResponse create(String eventUuid, Long budgetCategoryId, PaymentDtoRequest paymentDtoRequest) {
        BudgetCategory budgetCategory = budgetCategoryService.getModelById(eventUuid, budgetCategoryId);

        Payment payment = paymentMapper.mapToModel(paymentDtoRequest);
        payment.setId(null);
        payment.setBudgetCategory(budgetCategory);

        return paymentMapper.mapToDto(paymentRepository.save(payment));
    }

    @Override
    public PaymentDtoResponse update(String eventUuid, Long budgetCategoryId, PaymentDtoRequest paymentDtoRequest) {
        BudgetCategory budgetCategory = budgetCategoryService.getModelById(eventUuid, budgetCategoryId);
        Payment payment = getModelById(budgetCategory, paymentDtoRequest.getId());

        payment.setExpenseName(paymentDtoRequest.getExpenseName());
        payment.setDescription(paymentDtoRequest.getDescription());
        payment.setAmount(paymentDtoRequest.getAmount());

        return paymentMapper.mapToDto(paymentRepository.save(payment));
    }

    @Override
    public void deleteById(String eventUuid, Long budgetCategoryId, Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }

    private Payment getModelById(BudgetCategory budgetCategory, Long id) {
        return paymentRepository.findByIdAndAndBudgetCategory(id, budgetCategory)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Payment with id " + id + " for budget category " + budgetCategory.getId() + " is not found"));
    }
}
