package com.E_commerce.dto.Request;

import com.E_commerce.Enum.ProductStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ProductRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 2, max = 255, message = "Tên sản phẩm phải từ 2 đến 255 ký tự")
    private String name;
    @NotBlank(message = "Slug không được để trống")
    @Size(min = 2, max = 255, message = "Slug phải từ 2 đến 255 ký tự")
    private String slug;
    @Size(max = 1000, message = "Mô tả tối đa 1000 ký tự")
    private String description;
    @NotNull(message = "CategoryId không được để trống")
    private Long categoryId;
    private ProductStatus status;
}
