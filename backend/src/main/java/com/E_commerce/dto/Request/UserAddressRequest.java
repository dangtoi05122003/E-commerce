package com.E_commerce.dto.Request;

import com.E_commerce.Entity.UserEntity;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAddressRequest {
    private UserEntity user;
    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;
    @NotBlank(message = "Địa chỉ không được để trống")
    private String addressLine;
    @NotBlank(message = "Thành phố không được để trống")
    private String city;
    @NotBlank(message = "Quận/Huyện không được để trống")
    private String district;
    @NotBlank(message = "Phường/Xã không được để trống")
    private String ward;
    private Boolean isDefault;
    private Boolean isDeleted;
}