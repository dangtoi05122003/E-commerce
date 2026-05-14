package com.E_commerce.Mapper;

import com.E_commerce.Entity.UserAddress;
import com.E_commerce.dto.Response.UserAddressResponse;

public class UserAddressMapper {
    public static UserAddressResponse toResponse(UserAddress userAddress) {
        return  UserAddressResponse.builder()
            .phone(userAddress.getPhone())
            .addressLine(userAddress.getAddressLine())
            .city(userAddress.getCity())
            .district(userAddress.getDistrict())
            .ward(userAddress.getWard())
            .isDefault(userAddress.getIsDefault())
            .build();
    }
}