package com.E_commerce.Mapper;

import com.E_commerce.Entity.InventoryEntity;
import com.E_commerce.dto.Response.InventoryResponse;

public class InventoryMapper {
    public static InventoryResponse toResponse(InventoryEntity inventory) {
        return InventoryResponse.builder()
            .quantity(inventory.getQuantity())
            .variantId(inventory.getVariant().getId())
            .build();
    }
}
