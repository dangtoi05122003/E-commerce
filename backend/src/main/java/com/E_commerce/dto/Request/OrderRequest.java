package com.E_commerce.dto.Request;

import java.util.List;

import com.E_commerce.Entity.UserEntity;
import com.E_commerce.Enum.OrderStatus;

import lombok.Getter;

@Getter
public class OrderRequest {
    private UserEntity user;
    private String customerName;
    private String customerPhone;
    private String shippingAddress;
    private Double totalAmount;
    private OrderStatus status;
    private List<OrderItemRequest> items;
}
