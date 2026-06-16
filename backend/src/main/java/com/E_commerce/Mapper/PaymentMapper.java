package com.E_commerce.Mapper;

import com.E_commerce.Entity.PaymentEntity;
import com.E_commerce.dto.Response.PaymentResponse;

public class PaymentMapper {
    public static PaymentResponse toResponse(PaymentEntity payment) {
        return PaymentResponse.builder()
            .id(payment.getId())
            .orderId(payment.getOrder().getId())
            .amount(payment.getAmount())
            .status(payment.getStatus())
            .build();
    }
}
