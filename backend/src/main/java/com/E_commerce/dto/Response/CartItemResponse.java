package com.E_commerce.dto.Response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemResponse {
    private Long cartItemId;
    private Long productVariantId;
    private String productName;
    private int quantity;
    private Double unitPrice;
    private BigDecimal subtotal;
}
