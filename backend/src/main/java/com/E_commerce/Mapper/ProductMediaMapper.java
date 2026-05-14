package com.E_commerce.Mapper;

import com.E_commerce.Entity.ProductMediaEntity;
import com.E_commerce.dto.Response.ProductMediaResponse;

public class ProductMediaMapper {
    public static ProductMediaResponse toResponse(ProductMediaEntity productMedia) {
        return ProductMediaResponse.builder()
            .url(productMedia.getUrl())
            .isPrimary(productMedia.getIsPrimary())
            .productId(productMedia.getProduct().getId())
            .mediaType(productMedia.getMediaType())
            .status(productMedia.getStatus())
            .build();
    }
}
