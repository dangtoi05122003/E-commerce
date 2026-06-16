package com.E_commerce.Mapper;

import com.E_commerce.Entity.OrderEntity;
import com.E_commerce.dto.Response.OrderResponse;

public class OrderMapper {
    public static OrderResponse toResponse(OrderEntity order) {
        return OrderResponse.builder()
            .user(UserMapper.toResponse(order.getUser()))
            .customerName(order.getCustomerName())
            .customerPhone(order.getCustomerPhone())
            .shippingAddress(order.getShippingAddress())
            .totalAmount(order.getTotalAmount())
            .status(order.getStatus())
            .items(order.getOrderItems().stream().map(OrderItemMapper::toResponse).toList())
            .build();
    }
}
