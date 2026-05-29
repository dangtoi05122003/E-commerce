package com.E_commerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.E_commerce.dto.Request.IntrospectRequest;
import com.E_commerce.dto.Response.IntrospectResponse;

@Service
public class IntrospectService {

    @Autowired
    private TokenService tokenService;

    public IntrospectResponse introspect(IntrospectRequest request) {
        try {
            tokenService.verifyToken(request.getToken());
            return IntrospectResponse.builder().valid(true).build();
        } catch (Exception e) {
            return IntrospectResponse.builder().valid(false).build();
        }
    }
}
