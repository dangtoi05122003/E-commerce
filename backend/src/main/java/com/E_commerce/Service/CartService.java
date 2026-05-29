package com.E_commerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.E_commerce.Entity.CartEntity;
import com.E_commerce.Entity.UserEntity;
import com.E_commerce.Repository.CartRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    public CartEntity getOrCreateCart(UserEntity user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    CartEntity cart = new CartEntity();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }
}