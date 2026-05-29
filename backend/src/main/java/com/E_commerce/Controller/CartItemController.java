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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.E_commerce.Service.CartItemService;
import com.E_commerce.dto.Request.CartItemRequest;
import com.E_commerce.dto.Response.CartItemResponse;

@RequestMapping("/cartitem")
@RestController
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @PostMapping
    public CartItemResponse create(@RequestBody CartItemRequest request) {
        return cartItemService.addItem(request);
    }
    @PutMapping("/update/{cartItemId}")
    public CartItemResponse updateQuantity(@PathVariable Long cartItemId, @RequestParam int quantity) {
        return cartItemService.updateQuantity(cartItemId, quantity);
    }
    @GetMapping
    public List<CartItemResponse> getItems() {
        return cartItemService.getItems();
    }
    @DeleteMapping("/{cartItemId}")
    public void deleteItem(@PathVariable Long cartItemId) {
        cartItemService.deleteCartItemId(cartItemId);
    }
    @DeleteMapping("/clear")
    public void clearCart() {
        cartItemService.clearCartItem();
    }
}
