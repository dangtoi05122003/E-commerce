package com.E_commerce.Mapper;

import com.E_commerce.Entity.OrderItemEntity;
import com.E_commerce.dto.Response.OrderItemResponse;

public class OrderItemMapper {
    public static OrderItemResponse toResponse(OrderItemEntity orderItem) {
        return OrderItemResponse.builder()
            .orderId(orderItem.getOrder().getId())
            .productVariantId(orderItem.getProductVariant().getId())
            .quantity(orderItem.getQuantity())
            .price(orderItem.getPrice())
            .build();
    }
}
