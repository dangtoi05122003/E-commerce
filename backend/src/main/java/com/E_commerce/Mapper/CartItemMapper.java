package com.E_commerce.Mapper;

import java.math.BigDecimal;

import com.E_commerce.Entity.CartItemEntity;
import com.E_commerce.dto.Response.CartItemResponse;

public class CartItemMapper {
    public static CartItemResponse toResponse(CartItemEntity cartItem) {
        return CartItemResponse.builder()
            .cartItemId(cartItem.getId())
            .productVariantId(cartItem.getProductVariant().getId())
            .productName(cartItem.getProductVariant().getProduct().getName())
            .quantity(cartItem.getQuantity())
            .unitPrice(cartItem.getPriceSnapshot())
            .subtotal(BigDecimal.valueOf(cartItem.getPriceSnapshot()).multiply(BigDecimal.valueOf(cartItem.getQuantity())))
            .build();
    }
}
