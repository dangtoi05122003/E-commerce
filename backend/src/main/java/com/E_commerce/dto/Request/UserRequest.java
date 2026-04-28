package com.E_commerce.dto.Request;

import com.E_commerce.Enum.StatusUser;
import com.E_commerce.Enum.UserRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequest {
    @NotBlank(message = "Username không được để trống")
    @Size(min = 3, max = 50, message = "Username phải từ 3 đến 50 ký tự")
    private String username;
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, max = 80, message = "Mật khẩu phải từ 8 ký tự trở lên")
    private String password;
    @NotBlank(message = "Email không được để trống")
    @Pattern(regexp = "^[a-zA-Z0-9+._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2-6}$", message = "Email không hợp lệ")
    private String email;
    private UserRole role;
    private StatusUser status;
}
