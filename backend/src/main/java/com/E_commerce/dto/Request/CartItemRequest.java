package com.E_commerce.dto.Request;

import lombok.Getter;

@Getter
public class CartItemRequest {
    private Long cartId;
    private Long productVariantId;
    private int quantity;
}
