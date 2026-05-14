package com.E_commerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.E_commerce.Enum.CategoryStatus;
import com.E_commerce.Service.CategoryService;
import com.E_commerce.dto.Request.CategoryRequest;
import com.E_commerce.dto.Response.CategoryResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {
     @Autowired
     private CategoryService categoryService;
     @PostMapping
     public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest request) {
          return categoryService.createCategory(request);
     }
     @PutMapping("/{categoryId}")
     public CategoryResponse updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryRequest request) {
          return categoryService.updateCategory(categoryId, request);
     }
     @GetMapping("/{categoryId}")
     public CategoryResponse getCategoryById(@PathVariable Long categoryId) {
          return categoryService.getCategoryById(categoryId);
     }
     @GetMapping("/all")
     public List<CategoryResponse> getAllCategories() {
          return categoryService.getAllCategories();
     }
     @PutMapping("/{categoryId}/status")
     public CategoryResponse updateCategoryStatus(@PathVariable Long categoryId, @RequestParam CategoryStatus status) {
          return categoryService.updateCategoryStatus(categoryId, status);
     }
     @GetMapping("/slug/{slug}")
     public CategoryResponse getCategoryBySlug(@PathVariable String slug) {
         return categoryService.getCategoryBySlug(slug);
     }
}
