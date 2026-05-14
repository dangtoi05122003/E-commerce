package com.E_commerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.E_commerce.Entity.ProductMediaEntity;
import com.E_commerce.Enum.MediaStatus;

import io.lettuce.core.dynamic.annotation.Param;

public interface ProductMediaRepository extends  JpaRepository<ProductMediaEntity, Long>{
    @Modifying
    @Query("UPDATE ProductMediaEntity m SET m.isPrimary = false WHERE m.product.id = :productId")
    void resetPrimary(Long productId);
    @Query("SELECT pm FROM ProductMediaEntity pm WHERE pm.product.id = :productId AND pm.status = :status")
    List<ProductMediaEntity> findByProductIdAndStatus(@Param("productId") Long productId, @Param("status") MediaStatus status);
}
