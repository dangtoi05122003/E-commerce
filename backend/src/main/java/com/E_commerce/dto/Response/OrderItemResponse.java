package com.E_commerce.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemResponse {
    private Long orderId;
    private Long productVariantId;
    private int quantity;
    private Double price;
}
