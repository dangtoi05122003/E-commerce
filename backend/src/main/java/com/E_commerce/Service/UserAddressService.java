package com.E_commerce.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.E_commerce.Entity.UserAddress;
import com.E_commerce.Entity.UserEntity;
import com.E_commerce.Exception.AppException;
import com.E_commerce.Exception.ErrorCode;
import com.E_commerce.Mapper.UserAddressMapper;
import com.E_commerce.Repository.UserAddressRepository;
import com.E_commerce.Repository.UserRepository;
import com.E_commerce.dto.Request.UserAddressRequest;
import com.E_commerce.dto.Response.UserAddressResponse;
import static com.E_commerce.utils.SecurityUtil.getCurrentUserId;

import jakarta.transaction.Transactional;

@Service
public class UserAddressService {
    @Autowired
    private UserAddressRepository userAddressRepository;
    @Autowired
    private UserRepository userRepository;
    @Transactional
    public UserAddressResponse createUserAddress(UserAddressRequest request) {
        Long userId = getCurrentUserId();
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        UserAddress userAddress = new UserAddress();
        userAddress.setUser(user);
        userAddress.setPhone(request.getPhone());
        userAddress.setAddressLine(request.getAddressLine());
        userAddress.setCity(request.getCity());
        userAddress.setDistrict(request.getDistrict());
        userAddress.setWard(request.getWard());
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            userAddressRepository.clearDefaultByUserId(userId);
        }
        userAddress.setIsDefault(Boolean.TRUE.equals(request.getIsDefault()));
        userAddress.setIsDeleted(false);
        return UserAddressMapper.toResponse(userAddressRepository.save(userAddress));
    }
    public UserAddressResponse updateUserAddress(Long userAddressId, UserAddressRequest request) {
        Long userId = getCurrentUserId();
        UserAddress userAddress = userAddressRepository.findById(userAddressId).orElseThrow(()-> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        if (!userAddress.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        if (Boolean.TRUE.equals(userAddress.getIsDeleted())) {
            throw new AppException(ErrorCode.ADDRESS_ALREADY_DELETED);
        }
        userAddress.setPhone(request.getPhone());
        userAddress.setAddressLine(request.getAddressLine());
        userAddress.setCity(request.getCity());
        userAddress.setDistrict(request.getDistrict());
        userAddress.setWard(request.getWard());
        return UserAddressMapper.toResponse(userAddressRepository.save(userAddress));
    }
    public UserAddressResponse deleteUserAddress(Long userAddressId) {
        Long userId = getCurrentUserId();
        UserAddress userAddress = userAddressRepository.findById(userAddressId).orElseThrow(()-> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        if (!userAddress.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        if (Boolean.TRUE.equals(userAddress.getIsDeleted())) {
            throw new AppException(ErrorCode.ADDRESS_ALREADY_DELETED);
        }
        userAddress.setIsDeleted(true);
        return UserAddressMapper.toResponse(userAddressRepository.save(userAddress)); 
    }
    public List<UserAddressResponse> getAllAddressesByUserId() {
        Long userId = getCurrentUserId();
        userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userAddressRepository.findAllByUserIdAndIsDeletedFalse(userId).stream()
            .map(UserAddressMapper::toResponse)
            .collect(Collectors.toList());
    }
    public UserAddressResponse getAddressById(Long userAddressId) {
        Long userId = getCurrentUserId();
        UserAddress userAddress = userAddressRepository.findById(userAddressId).orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        if (!userAddress.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        if (Boolean.TRUE.equals(userAddress.getIsDeleted())) {
            throw new AppException(ErrorCode.ADDRESS_ALREADY_DELETED);
        }
        return UserAddressMapper.toResponse(userAddress);
    }
}
