package com.E_commerce.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.E_commerce.Entity.CartEntity;
import com.E_commerce.Entity.CartItemEntity;
import com.E_commerce.Entity.ProductVariantEntity;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long>{
    List<CartItemEntity> findByCart(CartEntity cart);
    Optional<CartItemEntity> findByCartAndProductVariant(CartEntity cart, ProductVariantEntity productVariant);
}
