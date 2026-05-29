package com.E_commerce.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.E_commerce.Entity.UserEntity;
import com.E_commerce.Enum.StatusUser;
import com.E_commerce.Enum.UserRole;
import com.E_commerce.Exception.AppException;
import com.E_commerce.Exception.ErrorCode;
import com.E_commerce.Mapper.UserMapper;
import com.E_commerce.Repository.UserRepository;
import com.E_commerce.dto.Request.user.UpdateUserNameRequest;
import com.E_commerce.dto.Request.user.UserRequest;
import com.E_commerce.dto.Response.UserResponse;
import static com.E_commerce.utils.SecurityUtil.getCurrentUserId;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CartService cartService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private final SecureRandom random = new SecureRandom();
    @Transactional
    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(UserRole.USER);
        user.setStatus(StatusUser.PENDING_VERIFY);
        userRepository.save(user);
        cartService.getOrCreateCart(user);
        String otp = generateOtp();
        String key = "otp:" + request.getEmail();
        redisTemplate.opsForValue().set(key, otp, 5, TimeUnit.MINUTES);
        emailService.sendOtpEmail(request.getEmail(), otp);
        return UserMapper.toResponse(user);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse changeStatusRole(Long userId, UserRole role) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setRole(role);
        return UserMapper.toResponse(userRepository.save(user));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUser() {
        return userRepository.findAll()
            .stream()
            .map(UserMapper::toResponse)
            .collect(Collectors.toList());
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getStatusUser(StatusUser status) {
        return userRepository.findByStatus(status)
            .stream()
            .map(UserMapper::toResponse)
            .collect(Collectors.toList());
    }
    public UserResponse updateUsername(UpdateUserNameRequest request) {
        Long userId = getCurrentUserId();
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if(user.getStatus() == StatusUser.DELETED) {
            throw new AppException(ErrorCode.INVALID_STATE);
        }
        if(request.getUsername().equals(user.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_NOT_CHANGED);
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        user.setUsername(request.getUsername());
        return UserMapper.toResponse(userRepository.save(user));
    }
    public UserResponse deleteUser() {
        Long userId = getCurrentUserId();
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setStatus(StatusUser.DELETED);
        return UserMapper.toResponse(userRepository.save(user));
    }
    public UserResponse verifyOtp(String email, String otp) {
        String key = "otp:" + email;
        String cachedOtp = redisTemplate.opsForValue().get(key);
        if (cachedOtp == null) {
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }
        if (!cachedOtp.equals(otp)) {
            throw new AppException(ErrorCode.OTP_INVALID);
        }
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setStatus(StatusUser.ACTIVE);
        userRepository.save(user);
        redisTemplate.delete(key);
        return UserMapper.toResponse(user);
    }
    private String generateOtp() {
        return String.valueOf(100000 + random.nextInt(900000));
    }
}