package com.E_commerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.E_commerce.Service.UserAddressService;
import com.E_commerce.dto.Request.UserAddressRequest;
import com.E_commerce.dto.Response.UserAddressResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user-address")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;
    @PostMapping("/{userId}")
    public UserAddressResponse create(@PathVariable Long userId, @Valid @RequestBody UserAddressRequest request) {
        return userAddressService.createUserAddress(userId, request);
    }
    @PutMapping("/{addressId}")
    public UserAddressResponse update(@PathVariable Long addressId, @Valid @RequestBody UserAddressRequest request) {
        return userAddressService.updateUserAddress(addressId, request);
    }
    @DeleteMapping("/{addressId}")
    public UserAddressResponse delete(@PathVariable Long addressId) {
        return userAddressService.deleteUserAddress(addressId);
    }
    @GetMapping("/user/{userId}")
    public List<UserAddressResponse> getAllByUser(@PathVariable Long userId) {
        return userAddressService.getAllAddressesByUserId(userId);
    }
    @GetMapping("/{addressId}")
    public UserAddressResponse getById(@PathVariable Long addressId) {
        return userAddressService.getAddressById(addressId);
    }
}
