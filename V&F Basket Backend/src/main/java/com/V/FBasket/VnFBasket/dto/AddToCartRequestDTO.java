package com.V.FBasket.VnFBasket.dto;

import lombok.Data;

@Data
public class AddToCartRequestDTO {
    private Long productId;
    private Integer quantity;
}
