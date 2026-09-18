package com.V.FBasket.VnFBasket.dto;

import com.V.FBasket.VnFBasket.model.CartItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartResponseDTO {
    private Long cartId;
    private List<CartItemResponseDTO> items;
    private Double totalAmount;

    public CartResponseDTO(Long cartId, List<CartItemResponseDTO> items, Double totalAmount) {
        this.cartId = cartId;
        this.items = items;
        this.totalAmount = totalAmount;
    }
}
