package com.E_commerce.Mapper;

import com.E_commerce.Entity.CategoryEntity;
import com.E_commerce.dto.Response.CategoryResponse;

public class CategoryMapper {
    public static CategoryResponse toResponse(CategoryEntity category){
        return CategoryResponse.builder()
            .name(category.getName())
            .slug(category.getSlug())
            .description(category.getDescription())
            .build();
    }
}
