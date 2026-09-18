package com.V.FBasket.VnFBasket.controller;

import com.V.FBasket.VnFBasket.serviceImpl.ProductImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vnfbasket")
public class ProductImageController {

    @Autowired
    private ProductImageServiceImpl productImageService;

    @GetMapping("/getProductsImageByProductId/{productId}")
    public ResponseEntity<?> getProductsImageByProductId(@PathVariable("productId") Long productId){
        return productImageService.getProductImageByProductId(productId);
    }

}
