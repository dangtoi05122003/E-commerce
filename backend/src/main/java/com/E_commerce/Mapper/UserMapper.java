package com.E_commerce.Mapper;

import com.E_commerce.Entity.UserEntity;
import com.E_commerce.dto.Response.UserResponse;

public class UserMapper {

    public static UserResponse toResponse(UserEntity user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .build();
    }
}