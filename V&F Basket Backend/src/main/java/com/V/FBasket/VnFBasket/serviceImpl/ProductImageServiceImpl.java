package com.V.FBasket.VnFBasket.serviceImpl;

import com.V.FBasket.VnFBasket.dao.ProductImageRepository;
import com.V.FBasket.VnFBasket.model.ProductImage;
import com.V.FBasket.VnFBasket.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;


    @Override
    public ResponseEntity<byte[]> getProductImageByProductId(Long productId) {
        ProductImage productImage = productImageRepository.findByProductProductId(productId).orElseThrow(() -> new RuntimeException("Product Image Not Found"));

        return ResponseEntity.ok().body(productImage.getImage());
    }
}
