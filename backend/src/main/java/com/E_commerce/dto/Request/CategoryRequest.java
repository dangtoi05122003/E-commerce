package com.E_commerce.dto.Request;

import com.E_commerce.Enum.CategoryStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
@Getter
public class CategoryRequest {
    @NotBlank(message = "Tên không được để trống")
    @Size(min = 2, max = 100, message = "Tên phải từ 2 đến 100 ký tự")
    private String name;
    @NotBlank(message = "Slug không được để trống")
    @Size(min = 2, max = 100, message = "Slug phải từ 2 đến 100 ký tự")
    private String slug;
    private String description;
    private CategoryStatus status;
}
