package com.V.FBasket.VnFBasket.dto;

import lombok.Data;

@Data
public class CartItemResponseDTO {
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private Double totalPrice;

    public CartItemResponseDTO(Long productId, String productName, Double productPrice, Integer quantity, Double totalPrice) {
        this.productId = productId;
        this.productName = productName;
        this.price = productPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
