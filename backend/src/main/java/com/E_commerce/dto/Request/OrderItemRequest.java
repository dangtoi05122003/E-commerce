package com.E_commerce.dto.Request;

import lombok.Getter;

@Getter
public class OrderItemRequest {
    private Long orderId;
    private Long productVariantId;
    private int quantity;
    private Double price;
}
