package com.E_commerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.E_commerce.Service.AuthService;
import com.E_commerce.Service.IntrospectService;
import com.E_commerce.dto.Request.AuthRequest;
import com.E_commerce.dto.Request.IntrospectRequest;
import com.E_commerce.dto.Request.LogoutRequest;
import com.E_commerce.dto.Response.AuthResponse;
import com.E_commerce.dto.Response.IntrospectResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private IntrospectService introspectService;
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest request) {
        authService.logout(request);
    }
    @PostMapping("/introspect")
    public IntrospectResponse introspect(@RequestBody IntrospectRequest request) {
        return introspectService.introspect(request);
    }
}
