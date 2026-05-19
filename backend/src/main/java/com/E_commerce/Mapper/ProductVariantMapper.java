package com.E_commerce.Mapper;

import com.E_commerce.Entity.ProductVariantEntity;
import com.E_commerce.dto.Response.ProductVariantResponse;

public class ProductVariantMapper {
    public static ProductVariantResponse toResponse(ProductVariantEntity productVariant) {
        return ProductVariantResponse.builder()
            .productName(productVariant.getProduct().getName())
            .price(productVariant.getPrice())
            .status(productVariant.getStatus())
            .attributes(productVariant.getAttributes())
            .build();
    }
}
