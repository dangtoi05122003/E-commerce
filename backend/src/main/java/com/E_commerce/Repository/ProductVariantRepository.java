package com.E_commerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.E_commerce.Entity.ProductVariantEntity;
import com.E_commerce.Enum.VariantStatus;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, Long>{
    List<ProductVariantEntity> findByStatus(VariantStatus status);
}
