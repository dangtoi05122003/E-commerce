package com.E_commerce.dto.Response;

import java.util.List;

import com.E_commerce.Enum.OrderStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderResponse {
    private UserResponse user;
    private String customerName;
    private String customerPhone;
    private String shippingAddress;
    private Double totalAmount;
    private OrderStatus status;
    private List<OrderItemResponse> items;
}