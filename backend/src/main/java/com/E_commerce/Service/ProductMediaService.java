package com.E_commerce.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.E_commerce.Entity.ProductEntity;
import com.E_commerce.Entity.ProductMediaEntity;
import com.E_commerce.Enum.MediaStatus;
import com.E_commerce.Enum.MediaType;
import com.E_commerce.Exception.AppException;
import com.E_commerce.Exception.ErrorCode;
import com.E_commerce.Mapper.ProductMediaMapper;
import com.E_commerce.Repository.ProductMediaRepository;
import com.E_commerce.Repository.ProductRepository;
import com.E_commerce.dto.Response.ProductMediaResponse;

@Service
public class ProductMediaService {
    @Autowired
    private ProductMediaRepository productMediaRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MinioService minioService;
    @Value("${minio.url}")
    private String minioUrl;
    @Value("${minio.bucket}")
    private String bucket;
    @Transactional
    public List<ProductMediaResponse> createProductMedia(Long productId, List<MultipartFile> files) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        List<ProductMediaResponse> result = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                String contentType = file.getContentType();
                MediaType mediaType;
                if (contentType != null && contentType.startsWith("video")) {
                    mediaType = MediaType.VIDEO;
                } else {
                    mediaType = MediaType.IMAGE;
                }
                String folder = (mediaType == MediaType.VIDEO)? "videos/": "images/";
                String objectName = "products/" + folder + fileName;
                minioService.uploadFile(objectName, file.getInputStream(), file.getContentType(),file.getSize());
                String fileUrl = minioUrl + "/" + bucket + "/" + objectName;
                ProductMediaEntity media = new ProductMediaEntity();
                media.setUrl(fileUrl);
                media.setIsPrimary(false);
                media.setProduct(product);
                media.setStatus(MediaStatus.ACTIVE);
                media.setMediaType(mediaType);
                result.add(ProductMediaMapper.toResponse(productMediaRepository.save(media)));
            } catch (Exception e) {
                throw new RuntimeException("Upload failed: " + e.getMessage());
            }
        }
        return result;
    }
    public List<ProductMediaResponse> getByProductId(Long productId) {
        if(!productRepository.existsById(productId)) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        return productMediaRepository
            .findByProductIdAndStatus(productId, MediaStatus.ACTIVE)
            .stream()
            .map(ProductMediaMapper::toResponse)
            .toList();
    }
    public ProductMediaResponse getProductMediaById(Long id) {
        ProductMediaEntity media = productMediaRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_MEDIA_NOT_FOUND));
        return ProductMediaMapper.toResponse(media);
    }
    @Transactional
    public ProductMediaResponse setPrimary(Long productMediaId) {
        ProductMediaEntity productMedia = productMediaRepository.findById(productMediaId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_MEDIA_NOT_FOUND));
        if (productMedia.getStatus() != MediaStatus.ACTIVE) {
            throw new AppException(ErrorCode.MEDIA_NOT_ACTIVE);
        }
        productMediaRepository.resetPrimary(productMedia.getProduct().getId());
        productMedia.setIsPrimary(true);
        return ProductMediaMapper.toResponse(productMediaRepository.save(productMedia));
    }
    public ProductMediaResponse updateStatus(Long productMediaId, MediaStatus status) {
        ProductMediaEntity productMedia = productMediaRepository.findById(productMediaId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_MEDIA_NOT_FOUND));
        productMedia.setStatus(status);
        return ProductMediaMapper.toResponse(productMediaRepository.save(productMedia));
    }
    public ProductMediaResponse deleteProductMedia(Long productMediaId) {
        ProductMediaEntity productMedia = productMediaRepository.findById(productMediaId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_MEDIA_NOT_FOUND));
        productMedia.setStatus(MediaStatus.HIDDEN);
        return ProductMediaMapper.toResponse(productMediaRepository.save(productMedia));
    }
}