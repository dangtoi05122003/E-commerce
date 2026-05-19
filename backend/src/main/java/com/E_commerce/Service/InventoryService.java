package com.E_commerce.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.E_commerce.Entity.InventoryEntity;
import com.E_commerce.Entity.ProductVariantEntity;
import com.E_commerce.Exception.AppException;
import com.E_commerce.Exception.ErrorCode;
import com.E_commerce.Mapper.InventoryMapper;
import com.E_commerce.Repository.InventoryRepository;
import com.E_commerce.Repository.ProductVariantRepository;
import com.E_commerce.dto.Response.InventoryResponse;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    public InventoryResponse create(Long variantId, Long quantity) {
        ProductVariantEntity variant = productVariantRepository.findById(variantId).orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
        if (inventoryRepository.findByVariantId(variantId).isPresent()) {
            throw new AppException(ErrorCode.INVENTORY_ALREADY_EXISTS);
        }
        InventoryEntity inventory = new InventoryEntity();
        inventory.setVariant(variant);
        inventory.setQuantity(quantity);
        return InventoryMapper.toResponse(inventoryRepository.save(inventory));
    }
    public InventoryResponse update(Long variantId, Long quantity) {
        InventoryEntity inventory = inventoryRepository.findByVariantId(variantId).orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));
        inventory.setQuantity(quantity);
        return InventoryMapper.toResponse(inventoryRepository.save(inventory));
    }
    public InventoryResponse getByVariant(Long variantId) {
        InventoryEntity inventory = inventoryRepository.findByVariantId(variantId).orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));
        return InventoryMapper.toResponse(inventory);
    }
    public void delete(Long variantId) {
        InventoryEntity inventory = inventoryRepository.findByVariantId(variantId).orElseThrow(() -> new AppException(ErrorCode.INVENTORY_NOT_FOUND));
        inventoryRepository.delete(inventory);
    }
    public List<InventoryResponse> findAll() {
        return inventoryRepository.findAll().stream().map(InventoryMapper::toResponse).toList();
    }
}
