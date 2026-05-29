package com.E_commerce.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.E_commerce.Entity.CartEntity;
import com.E_commerce.Entity.CartItemEntity;
import com.E_commerce.Entity.ProductVariantEntity;
import com.E_commerce.Exception.AppException;
import com.E_commerce.Exception.ErrorCode;
import com.E_commerce.Mapper.CartItemMapper;
import com.E_commerce.Repository.CartItemRepository;
import com.E_commerce.Repository.CartRepository;
import com.E_commerce.Repository.ProductVariantRepository;
import com.E_commerce.dto.Request.CartItemRequest;
import com.E_commerce.dto.Response.CartItemResponse;
import static com.E_commerce.utils.SecurityUtil.getCurrentUserId;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    private CartEntity getCurrentCart() {
        Long userId = getCurrentUserId();
        return cartRepository.findByUserId(userId).orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));}
    public CartItemResponse addItem(CartItemRequest request) {
        CartEntity cart = getCurrentCart();
        ProductVariantEntity variant = productVariantRepository.findById(request.getProductVariantId()).orElseThrow(() -> new AppException(ErrorCode.VARIANT_NOT_FOUND));
        CartItemEntity existingItem = cartItemRepository.findByCartAndProductVariant(cart, variant).orElse(null);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            return CartItemMapper.toResponse(cartItemRepository.save(existingItem));
        }
        CartItemEntity item = new CartItemEntity();
        item.setCart(cart);
        item.setProductVariant(variant);
        item.setQuantity(request.getQuantity());
        item.setPriceSnapshot(variant.getPrice());
        return CartItemMapper.toResponse(cartItemRepository.save(item));
    }
    public CartItemResponse updateQuantity(Long cartItemId, int quantity) {
        CartEntity cart = getCurrentCart();
        CartItemEntity item = cartItemRepository.findById(cartItemId).orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));
        if (!item.getCart().getId().equals(cart.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        if (quantity <= 0) {
            cartItemRepository.delete(item);
            return null;
        }
        item.setQuantity(quantity);
        return CartItemMapper.toResponse(cartItemRepository.save(item));
    }
    public List<CartItemResponse> getItems() {
        CartEntity cart = getCurrentCart();
        return cartItemRepository.findByCart(cart)
                .stream()
                .map(CartItemMapper::toResponse)
                .toList();
    }
    public void deleteCartItemId(Long cartItemId) {
        CartEntity cart = getCurrentCart();
        CartItemEntity item = cartItemRepository.findById(cartItemId).orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));
        if (!item.getCart().getId().equals(cart.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        cartItemRepository.delete(item);
    }
    public void clearCartItem() {
        CartEntity cart = getCurrentCart();
        List<CartItemEntity> items = cartItemRepository.findByCart(cart);
        cartItemRepository.deleteAll(items);
    }
}
