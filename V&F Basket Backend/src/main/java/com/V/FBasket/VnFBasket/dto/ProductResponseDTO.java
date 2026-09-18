package com.V.FBasket.VnFBasket.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ProductResponseDTO {
    private Long productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private int stockQuantity;
    private int productRating;
    private String productImageUrl;
    private long categoryId;
}
