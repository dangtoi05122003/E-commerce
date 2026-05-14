package com.E_commerce.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.E_commerce.Entity.CategoryEntity;
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{
    Optional<CategoryEntity> findBySlug(String slug);
}
