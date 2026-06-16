package com.E_commerce.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.E_commerce.Entity.CategoryEntity;
import com.E_commerce.Entity.ProductEntity;
import com.E_commerce.Enum.ProductStatus;
import com.E_commerce.Exception.AppException;
import com.E_commerce.Exception.ErrorCode;
import com.E_commerce.Mapper.ProductMapper;
import com.E_commerce.Repository.CategoryRepository;
import com.E_commerce.Repository.ProductRepository;
import com.E_commerce.dto.Request.ProductRequest;
import com.E_commerce.dto.Response.ProductResponse;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    public ProductResponse createProduct(ProductRequest request) {
        CategoryEntity category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        ProductEntity product = new ProductEntity();
        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setDescription(request.getDescription());
        product.setCategory(category);
        product.setStatus(ProductStatus.ACTIVE);
        return ProductMapper.toResponse(productRepository.save(product));
    }
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        CategoryEntity category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setDescription(request.getDescription());
        product.setCategory(category);
        return ProductMapper.toResponse(productRepository.save(product));
    }
    @Cacheable(value = "product", key = "#productId")
    public ProductResponse getProductById(Long productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return ProductMapper.toResponse(product);
    }
    public ProductResponse updateProductStatus(Long productId, ProductStatus status) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setStatus(status);
        return ProductMapper.toResponse(productRepository.save(product));
    } 
    public List<ProductResponse> getAllProduct() {
        return productRepository.findAll().stream().map(ProductMapper::toResponse).toList();
    }
    @Cacheable("visible_products")
    public List<ProductResponse> getVisibleProducts() {
        return productRepository.findByStatus(ProductStatus.ACTIVE).stream().map(ProductMapper::toResponse).toList();
    }
    public Page<ProductResponse> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByStatusNot(ProductStatus.HIDDEN, pageable).map(ProductMapper::toResponse);
    }
    public ProductResponse restoreProduct(Long productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setStatus(ProductStatus.ACTIVE);
        return ProductMapper.toResponse(productRepository.save(product));
    }
    public List<ProductResponse> searchProduct(String keyword) {
        return productRepository.search(keyword, ProductStatus.HIDDEN)
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }
    public ProductResponse deleteProduct(Long productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        if (product.getStatus() == ProductStatus.HIDDEN) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_DELETED);
        }
        product.setStatus(ProductStatus.HIDDEN);
        return ProductMapper.toResponse(productRepository.save(product));
    }
}
