package com.E_commerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.E_commerce.Service.InventoryService;
import com.E_commerce.dto.Request.InventoryRequest;
import com.E_commerce.dto.Response.InventoryResponse;

@RestController
@RequestMapping("/inventories")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    @PostMapping
    public InventoryResponse create(@RequestBody InventoryRequest request) {
        return inventoryService.create(request.getVariantId(), request.getQuantity());
    }
    @PutMapping
    public InventoryResponse update(@RequestBody InventoryRequest request) {
        return inventoryService.update(request.getVariantId(), request.getQuantity());
    }
    @GetMapping("/{variantId}")
    public InventoryResponse getByVariant(@PathVariable Long variantId) {
        return inventoryService.getByVariant(variantId);
    }
    @DeleteMapping("/{variantId}")
    public void delete(@PathVariable Long variantId) {
        inventoryService.delete(variantId);
    }
    @GetMapping
    public List<InventoryResponse> findAll() {
        return inventoryService.findAll();
    }
}
