package com.E_commerce.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAddressResponse {
    private String phone;
    private String addressLine;
    private String city;
    private String district;
    private String ward;
    private Boolean isDefault;
}
