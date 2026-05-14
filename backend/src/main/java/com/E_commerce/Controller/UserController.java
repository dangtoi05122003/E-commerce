package com.E_commerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.E_commerce.Enum.StatusUser;
import com.E_commerce.Enum.UserRole;
import com.E_commerce.Service.UserService;
import com.E_commerce.dto.Request.UserRequest;
import com.E_commerce.dto.Response.UserResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody UserRequest request) {
        return userService.createUser(request);
    }
    @PostMapping("/verify-otp")
    public UserResponse verifyOtp(@RequestParam String email, @RequestParam String otp) {
        return userService.verifyOtp(email, otp);
    }
    @PutMapping("/{userId}/role")
    public UserResponse changeRole(@PathVariable Long userId, @RequestParam UserRole role) {
        return userService.changeStatusRole(userId, role);
    }
    @GetMapping("/all")
    public List<UserResponse> getAllUser() {
        return userService.getAllUser();
    }
    @GetMapping("/status")
    public List<UserResponse> getAllStatusUser(@RequestParam StatusUser status) {
        return userService.getStatusUser(status);
    }
    @PutMapping("/update/{userId}")
    public UserResponse updateUsername(@PathVariable Long userId, @Valid @RequestBody UserRequest request) {
        return userService.updateUsername(userId, request);
    }
    @PutMapping("/delete/{userId}")
    public UserResponse deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}