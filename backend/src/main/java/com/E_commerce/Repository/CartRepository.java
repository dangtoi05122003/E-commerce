package com.E_commerce.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.E_commerce.Entity.CartEntity;
import com.E_commerce.Entity.UserEntity;
@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long>{
    Optional<CartEntity> findByUser(UserEntity user);
    Optional<CartEntity> findByUserId(Long userId);
}
