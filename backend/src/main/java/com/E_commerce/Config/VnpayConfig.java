package com.E_commerce.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class VnpayConfig {
    @Value("${vnpay.tmn-code}")
    private String tmnCode;
    @Value("${vnpay.hash-secret}")
    private String hashSecret;
    @Value("${vnpay.pay-url}")
    private String payUrl;
    @Value("${vnpay.return-url}")
    private String returnUrl;
    @Value("${vnpay.ipn-url}")
    private String ipnUrl;

}
