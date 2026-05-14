package com.E_commerce.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {
    private String name;
    private String slug;
    private String description;
}
