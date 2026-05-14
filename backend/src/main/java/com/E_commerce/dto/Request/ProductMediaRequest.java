package com.E_commerce.dto.Request;

import com.E_commerce.Enum.MediaType;

import lombok.Getter;

@Getter
public class ProductMediaRequest {
    private String url;
    private Boolean isPrimary;
    private Long productId;
    private MediaType mediaType;
}
