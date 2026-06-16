package com.E_commerce.dto.Response;

import com.E_commerce.Enum.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String name;
    private String slug;
    private String description;
    private CategoryResponse category;
    private ProductStatus status;
}
