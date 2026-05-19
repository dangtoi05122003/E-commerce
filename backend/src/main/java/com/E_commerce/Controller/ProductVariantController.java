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

import com.E_commerce.Enum.VariantStatus;
import com.E_commerce.Service.ProductVariantService;
import com.E_commerce.dto.Request.ProductVariantRequest;
import com.E_commerce.dto.Response.ProductVariantResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/variants")
public class ProductVariantController {
    @Autowired
    private ProductVariantService productVariantService;
    @PostMapping
    public ProductVariantResponse create(@Valid @RequestBody ProductVariantRequest request) {
        return productVariantService.create(request);
    }
    @PutMapping("/{id}")
    public ProductVariantResponse update(@PathVariable Long id, @Valid @RequestBody ProductVariantRequest request) {
        return productVariantService.update(id, request);
    }
    @PutMapping("/delete/{id}")
    public ProductVariantResponse delete(@PathVariable Long id) {
        return productVariantService.delete(id);
    }
    @GetMapping("/{id}")
    public ProductVariantResponse findById(@PathVariable Long id) {
        return productVariantService.findById(id);
    }
    @GetMapping
    public List<ProductVariantResponse> findAll() {
        return productVariantService.findAll();
    }
    @PutMapping("/{id}/status")
    public ProductVariantResponse updateStatus(@PathVariable Long id, @RequestParam VariantStatus status) {
        return productVariantService.updateStatus(id, status);
    }
}
