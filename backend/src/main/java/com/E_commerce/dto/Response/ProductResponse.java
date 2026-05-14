package com.E_commerce.dto.Response;

import com.E_commerce.Entity.CategoryEntity;
import com.E_commerce.Enum.ProductStatus;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class ProductResponse {
    private String name;
    private String slug;
    private String description;
    private CategoryEntity category;
    private ProductStatus status;
}
