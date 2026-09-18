package com.V.FBasket.VnFBasket.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductRequest {

    private Long productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private int stockQuantity;
    private Long categoryId;
    private MultipartFile productImage;

    
}
