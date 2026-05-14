package com.E_commerce.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.E_commerce.Entity.ProductEntity;
import com.E_commerce.Enum.ProductStatus;

import io.lettuce.core.dynamic.annotation.Param;
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
    List<ProductEntity> findByStatus(ProductStatus status);
    Page<ProductEntity> findByStatusNot(ProductStatus status, Pageable pageable);
    @Query("SELECT p FROM ProductEntity p WHERE (LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.slug) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND p.status <> :status")
    List<ProductEntity> search(@Param("keyword") String keyword, @Param("status") ProductStatus status);
}
