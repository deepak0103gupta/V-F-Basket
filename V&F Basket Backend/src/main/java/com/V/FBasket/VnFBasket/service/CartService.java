package com.V.FBasket.VnFBasket.service;

import com.V.FBasket.VnFBasket.dto.AddToCartRequestDTO;
import com.V.FBasket.VnFBasket.dto.CartResponseDTO;

public interface CartService {
    public void addToCart(Long userId, AddToCartRequestDTO addToCartRequestDTO);
    public void updateCartItem(Long userId, Long productId, Integer quantity);
    public void removeFromCart(Long userId, Long productId);
    public void clearCart(Long userId);
    public CartResponseDTO getCart(Long userId);
}
