package com.E_commerce.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.E_commerce.Entity.OrderEntity;
import com.E_commerce.Entity.OrderItemEntity;
import com.E_commerce.Entity.ProductVariantEntity;
import com.E_commerce.Entity.UserEntity;
import com.E_commerce.Enum.OrderStatus;
import com.E_commerce.Exception.AppException;
import com.E_commerce.Exception.ErrorCode;
import com.E_commerce.Mapper.OrderMapper;
import com.E_commerce.Repository.OrderRepository;
import com.E_commerce.Repository.ProductVariantRepository;
import com.E_commerce.Repository.UserRepository;
import com.E_commerce.dto.Request.OrderItemRequest;
import com.E_commerce.dto.Request.OrderRequest;
import com.E_commerce.dto.Response.OrderResponse;
import static com.E_commerce.utils.SecurityUtil.getCurrentUserId;
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    public OrderResponse createOrder(OrderRequest request) {
        Long userId = getCurrentUserId();
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setCustomerName(request.getCustomerName());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setShippingAddress(request.getShippingAddress());
        order.setStatus(OrderStatus.PENDING);
        double total = 0;
        List<OrderItemEntity> orderItems = new ArrayList<>();
        for (OrderItemRequest itemReq : request.getItems()) {
            ProductVariantEntity variant = productVariantRepository.findById(itemReq.getProductVariantId()).orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
            OrderItemEntity item = new OrderItemEntity();
            item.setOrder(order);
            item.setProductVariant(variant);
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(variant.getPrice());
            total += variant.getPrice() * itemReq.getQuantity();
            orderItems.add(item);
        }
        order.setOrderItems(orderItems);
        order.setTotalAmount(total);
        return OrderMapper.toResponse(orderRepository.save(order));
        }
}
