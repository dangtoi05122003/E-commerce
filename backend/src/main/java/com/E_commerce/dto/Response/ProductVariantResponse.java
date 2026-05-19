package com.E_commerce.dto.Response;

import java.util.Map;

import com.E_commerce.Enum.AttributeKey;
import com.E_commerce.Enum.VariantStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductVariantResponse {
    private String productName;
    private Double price;
    private VariantStatus status;
    private Map<AttributeKey, String> attributes;
}
