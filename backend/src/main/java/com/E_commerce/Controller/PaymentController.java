package com.E_commerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.E_commerce.Service.VnpayService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private VnpayService vnpayService;
    @GetMapping("/vnpay/return")
    public String vnpayReturn(HttpServletRequest request) {
        return vnpayService.handleReturn(request);
    }
    @PostMapping("/create/{orderId}")
    public String createPayment(@PathVariable Long orderId, @RequestParam(required = false) String bankCode, @RequestParam(required = false, defaultValue = "vn") String language, HttpServletRequest request) {
        return vnpayService.createPayment(orderId, bankCode, language, request);
    }
}