package com.V.FBasket.VnFBasket.service;

import java.io.IOException;
import java.util.List;

import com.V.FBasket.VnFBasket.dto.ProductRequest;
import com.V.FBasket.VnFBasket.dto.ProductResponseDTO;
import com.V.FBasket.VnFBasket.model.Products;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    Products addProducts(ProductRequest productRequest, MultipartFile file) throws IOException;
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductByProductName(String productName);
    List<Products> getProductsByCategoryId(Long categoryId);
    ProductResponseDTO getProductById(Long productId);
    Products updateProduct(ProductRequest product) throws  IOException;
    boolean deleteProduct(Long productId);
    List<ProductResponseDTO> getProductsByCategoryName(String categoryName);

    
} 
