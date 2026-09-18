package com.V.FBasket.VnFBasket.service;

import com.V.FBasket.VnFBasket.model.ProductImage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductImageService {
    ResponseEntity<byte[]> getProductImageByProductId(Long productId);
}
