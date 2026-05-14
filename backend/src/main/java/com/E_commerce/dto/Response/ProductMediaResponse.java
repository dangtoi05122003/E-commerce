package com.E_commerce.dto.Response;

import com.E_commerce.Enum.MediaStatus;
import com.E_commerce.Enum.MediaType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductMediaResponse {
    private String url;
    private Boolean isPrimary;
    private Long productId;
    private MediaType mediaType;
    private MediaStatus status;
}
