package com.E_commerce.dto.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class InventoryRequest {
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0")
    private Long quantity;
    @NotNull(message = "VariantId không được để trống")
    private Long variantId;
}
