package com.E_commerce.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InventoryResponse {
    private Long quantity;
    private Long variantId;
}
