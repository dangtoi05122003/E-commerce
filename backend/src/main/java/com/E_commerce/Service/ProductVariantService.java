package com.E_commerce.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.E_commerce.Entity.ProductEntity;
import com.E_commerce.Entity.ProductVariantEntity;
import com.E_commerce.Enum.VariantStatus;
import com.E_commerce.Exception.AppException;
import com.E_commerce.Exception.ErrorCode;
import com.E_commerce.Mapper.ProductVariantMapper;
import com.E_commerce.Repository.ProductRepository;
import com.E_commerce.Repository.ProductVariantRepository;
import com.E_commerce.dto.Request.ProductVariantRequest;
import com.E_commerce.dto.Response.ProductVariantResponse;

@Service
public class ProductVariantService {
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private ProductRepository productRepository;

    public ProductVariantResponse create(ProductVariantRequest request) {
        ProductEntity product = productRepository.findById(request.getProductId()).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        ProductVariantEntity productVariant = new ProductVariantEntity();
        productVariant.setProduct(product);
        productVariant.setPrice(request.getPrice());
        productVariant.setStatus(VariantStatus.ACTIVE);
        productVariant.setAttributes(request.getAttributes());
        return ProductVariantMapper.toResponse(productVariantRepository.save(productVariant));
    }
    public ProductVariantResponse update(Long id, ProductVariantRequest request) {
        ProductEntity product = productRepository.findById(request.getProductId()).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        ProductVariantEntity variant = productVariantRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
        if (variant.getStatus() == VariantStatus.INACTIVE) {
            throw new AppException(ErrorCode.VARIANT_INACTIVE);
        }
        variant.setProduct(product);
        variant.setPrice(request.getPrice());
        variant.setAttributes(request.getAttributes());
        return ProductVariantMapper.toResponse(productVariantRepository.save(variant));
    }
    public ProductVariantResponse delete(Long id) {
        ProductVariantEntity variant = productVariantRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
        variant.setStatus(VariantStatus.INACTIVE);
        return ProductVariantMapper.toResponse(productVariantRepository.save(variant));
    }
    public ProductVariantResponse findById(Long id) {
        return ProductVariantMapper.toResponse(productVariantRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND)));
    }
    public List<ProductVariantResponse> findAll() {
        return productVariantRepository.findByStatus(VariantStatus.ACTIVE).stream().map(ProductVariantMapper::toResponse).toList();
    }
    public ProductVariantResponse updateStatus(Long id, VariantStatus status) {
        ProductVariantEntity productVariant = productVariantRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.VARIANT_NOT_FOUND));
        productVariant.setStatus(status);
        return ProductVariantMapper.toResponse(productVariantRepository.save(productVariant));
    }
}
