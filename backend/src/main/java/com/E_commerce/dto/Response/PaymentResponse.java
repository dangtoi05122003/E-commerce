package com.E_commerce.dto.Response;

import com.E_commerce.Enum.PaymentStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentResponse {
    private Long id;
    private Long orderId;
    private Double amount;
    private PaymentStatus status;
}
