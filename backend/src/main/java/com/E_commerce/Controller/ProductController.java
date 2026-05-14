package com.E_commerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.E_commerce.Enum.ProductStatus;
import com.E_commerce.Service.ProductService;
import com.E_commerce.dto.Request.ProductRequest;
import com.E_commerce.dto.Response.ProductResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return productService.updateProduct(id, request);
    }
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
    @GetMapping("/all")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProduct();
    }
    @GetMapping("/visible")
    public List<ProductResponse> getVisibleProducts() {
        return productService.getVisibleProducts();
    }
    @GetMapping
    public Page<ProductResponse> getProducts(@RequestParam int page, @RequestParam int size) {
        return productService.getProducts(page, size);
    }
    @GetMapping("/search")
    public List<ProductResponse> searchProduct(@RequestParam String keyword) {
        return productService.searchProduct(keyword);
    }
    @PatchMapping("/{id}/status")
    public ProductResponse updateStatus(@PathVariable Long id, @RequestParam ProductStatus status) {
        return productService.updateProductStatus(id, status);
    }
    @PutMapping("/delete/{id}")
    public ProductResponse deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
    @PatchMapping("/{id}/restore")
    public ProductResponse restoreProduct(@PathVariable Long id) {
        return productService.restoreProduct(id);
    }
}