package com.E_commerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.E_commerce.Enum.MediaStatus;
import com.E_commerce.Service.ProductMediaService;
import com.E_commerce.dto.Response.ProductMediaResponse;

@RestController
@RequestMapping("/product-media")
public class ProductMediaController {
    @Autowired
    private ProductMediaService productMediaService;
    @PostMapping("/{productId}/media")
    public List<ProductMediaResponse> create(@PathVariable Long productId,@RequestParam("files") List<MultipartFile> files) {
        return productMediaService.createProductMedia(productId, files);
    }
    @GetMapping("/product/{productId}")
    public List<ProductMediaResponse> getByProductId(@PathVariable Long productId) {
        return productMediaService.getByProductId(productId);
    }
    @GetMapping("/{id}")
    public ProductMediaResponse getById(@PathVariable Long id) {
        return productMediaService.getProductMediaById(id);
    }
    @PutMapping("/{id}/primary")
    public ProductMediaResponse setPrimary(@PathVariable Long id) {
        return productMediaService.setPrimary(id);
    }
    @PutMapping("/{id}/status")
    public ProductMediaResponse updateStatus(@PathVariable Long id, @RequestParam MediaStatus status) {
        return productMediaService.updateStatus(id, status);
    }
    @PutMapping("/{id}")
    public ProductMediaResponse delete(@PathVariable Long id) {
        return productMediaService.deleteProductMedia(id);
    }
}
