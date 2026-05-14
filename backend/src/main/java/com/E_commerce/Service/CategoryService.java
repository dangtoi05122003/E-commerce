package com.E_commerce.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.E_commerce.Entity.CategoryEntity;
import com.E_commerce.Enum.CategoryStatus;
import com.E_commerce.Exception.AppException;
import com.E_commerce.Exception.ErrorCode;
import com.E_commerce.Mapper.CategoryMapper;
import com.E_commerce.Repository.CategoryRepository;
import com.E_commerce.dto.Request.CategoryRequest;
import com.E_commerce.dto.Response.CategoryResponse;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public CategoryResponse createCategory(CategoryRequest request) {
        CategoryEntity category = new CategoryEntity();
        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setDescription(request.getDescription());
        category.setStatus(CategoryStatus.VISIBLE);
        return CategoryMapper.toResponse(categoryRepository.save(category));
    }
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        if (category.getStatus() == CategoryStatus.HIDDEN) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setDescription(request.getDescription());
        return CategoryMapper.toResponse(categoryRepository.save(category));
    }
    public CategoryResponse getCategoryById(Long id) {
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        if (category.getStatus() == CategoryStatus.HIDDEN) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        return CategoryMapper.toResponse(category);
    }
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
            .stream()
            .filter(c -> c.getStatus() == CategoryStatus.VISIBLE)
            .map(CategoryMapper::toResponse)
            .toList();
    }
    public CategoryResponse updateCategoryStatus(Long CategoryId, CategoryStatus status) {
        CategoryEntity category = categoryRepository.findById(CategoryId).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setStatus(status);
        return CategoryMapper.toResponse(categoryRepository.save(category));
    }
    public CategoryResponse getCategoryBySlug(String slug) {
        CategoryEntity category = categoryRepository.findBySlug(slug).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        if (category.getStatus() == CategoryStatus.HIDDEN) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        return CategoryMapper.toResponse(category);
    }
}
