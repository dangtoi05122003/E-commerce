package com.E_commerce.dto.Response;

import com.E_commerce.Enum.StatusUser;
import com.E_commerce.Enum.UserRole;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private String username;
    private String email;
    private UserRole role;
    private StatusUser status;
}
