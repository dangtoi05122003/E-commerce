package com.E_commerce.Mapper;

import com.E_commerce.Entity.ProductEntity;
import com.E_commerce.dto.Response.ProductResponse;

public class ProductMapper {
    public static ProductResponse toResponse(ProductEntity product) {
        return ProductResponse.builder()
            .name(product.getName())
            .slug(product.getSlug())
            .description(product.getDescription())
            .category(product.getCategory())
            .status(product.getStatus())
            .build();
    }
}
