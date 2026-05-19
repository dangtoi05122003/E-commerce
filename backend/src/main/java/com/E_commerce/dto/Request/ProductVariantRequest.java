package com.E_commerce.dto.Request;

import java.util.Map;

import com.E_commerce.Enum.AttributeKey;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class ProductVariantRequest {
    @NotNull(message = "ProductId không được để trống")
    private Long productId;
    @NotNull(message = "Giá không được để trống")
    @Positive(message = "Giá phải lớn hơn 0")
    private Double price;
    @NotNull(message = "Attributes không được để trống")
    private Map<AttributeKey, String> attributes;
}
