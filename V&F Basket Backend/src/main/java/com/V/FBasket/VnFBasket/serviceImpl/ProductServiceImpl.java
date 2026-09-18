package com.V.FBasket.VnFBasket.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.V.FBasket.VnFBasket.dao.ProductImageRepository;
import com.V.FBasket.VnFBasket.dto.ProductResponseDTO;
import com.V.FBasket.VnFBasket.model.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.V.FBasket.VnFBasket.dto.ProductRequest;
import com.V.FBasket.VnFBasket.dao.CategoriesRepository;
import com.V.FBasket.VnFBasket.dao.ProductsRepository;
import com.V.FBasket.VnFBasket.model.Categories;
import com.V.FBasket.VnFBasket.model.Products;
import com.V.FBasket.VnFBasket.service.ProductService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductServiceImpl implements ProductService {

@Autowired
private ProductsRepository productsRepo;
@Autowired
private CategoriesRepository categoryryRepo;
@Autowired
private ProductImageRepository productImgRepo;

@Override
@Transactional
public Products addProducts(ProductRequest productRequest,  MultipartFile file) throws IOException {
    try {
        Categories category = categoryryRepo.findById(productRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));

        System.out.println("File is "+(file==null));
        if(file!=null && !file.isEmpty()) {
            if(!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")){
                throw new RuntimeException("Only PNG and JPEG files are allowed");
            }
        }
        Products product = new Products();
        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());
        product.setProductPrice(productRequest.getProductPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(category);
        productsRepo.save(product);

        if(file!=null && !file.isEmpty()) {
            ProductImage productImage = new ProductImage();
            productImage.setImage(file.getBytes());
            productImage.setProduct(product);
            productImgRepo.save(productImage);
        }
        return product;
    } catch (Exception e) {
        throw new RuntimeException("Error While Adding Product", e);
    }
}

@Override
public List<ProductResponseDTO> getAllProducts() {
    try {
        List<Products> products = productsRepo.findAll();
        return products.stream().map(product -> {
            ProductResponseDTO productResponse = new ProductResponseDTO();
            productResponse.setProductId(product.getProductId());
            productResponse.setProductName(product.getProductName());
            productResponse.setProductDescription(product.getProductDescription());
            productResponse.setProductPrice(product.getProductPrice());
            productResponse.setStockQuantity(product.getStockQuantity());
            productResponse.setProductRating(product.getProductRating());
            productResponse.setProductImageUrl("http://localhost:8080/vnfbasket/getProductsImageByProductId/"+product.getProductId());
            return productResponse;
        }).toList();
    } catch (Exception e) {
        return null;
    }
}

@Override
public List<Products> getProductsByCategoryId(Long categoryId) {
  try{
    return productsRepo.findByCategoryCategoryId(categoryId);
  } catch (Exception e) {
        return null;
    }
}

@Override
public ProductResponseDTO getProductById(Long productId) {

    Products product = productsRepo.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    ProductResponseDTO productResponse = new ProductResponseDTO();

    productResponse.setProductId(product.getProductId());
    productResponse.setProductName(product.getProductName());
    productResponse.setProductDescription(product.getProductDescription());
    productResponse.setProductPrice(product.getProductPrice());
    productResponse.setStockQuantity(product.getStockQuantity());
    productResponse.setProductRating(product.getProductRating());

    productResponse.setProductImageUrl(
        "http://localhost:8080/vnfbasket/getProductsImageByProductId/" + product.getProductId()
    );

    
    productResponse.setCategoryId(product.getCategory().getCategoryId());

    return productResponse;
}

@Override
@Transactional
public Products updateProduct(ProductRequest product) throws IOException {
    try{
        MultipartFile file = product.getProductImage();
        Products existingProduct = productsRepo.findById(product.getProductId()).orElseThrow(()-> new RuntimeException("Product Not Found"));
        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductDescription(product.getProductDescription());
        existingProduct.setProductPrice(product.getProductPrice());
        existingProduct.setStockQuantity(product.getStockQuantity());
            Categories category = categoryryRepo.findById(product.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
            existingProduct.setCategory(category);




        if(file!=null && !file.isEmpty()){
            if(!file.getContentType().equals("image/png") && !file.getContentType().equals("image/jpeg")){
                throw new RuntimeException("Only PNG and JPEG files are allowed");
            }
            Optional<ProductImage> productImageExists = productImgRepo.findByProductProductId(product.getProductId());
            ProductImage productImage;
            if(productImageExists.isPresent()){
                productImage = productImageExists.get();
                productImage.setImage(file.getBytes());
            }
            else{
                productImage = new ProductImage();
                productImage.setProduct(existingProduct);
            }
            productImage.setImage(file.getBytes());
            productImgRepo.save(productImage);
        }
        return productsRepo.save(existingProduct);
    }
    catch(Exception e){
        return null;
    }
}

@Override
public boolean deleteProduct(Long productId) {
    try {
        productsRepo.deleteById(productId);
        return true;
    } catch (Exception e) {
        return false;
    }
}

    @Override
    public List<ProductResponseDTO> getProductsByCategoryName(String categoryName) {
        try{
            List<Products> products = productsRepo.findProductsByCategoryName(categoryName);
            return products.stream().map(product -> {
                ProductResponseDTO productResponse = new ProductResponseDTO();
                productResponse.setProductId(product.getProductId());
                productResponse.setProductName(product.getProductName());
                productResponse.setProductDescription(product.getProductDescription());
                productResponse.setProductPrice(product.getProductPrice());
                productResponse.setStockQuantity(product.getStockQuantity());
                productResponse.setProductRating(product.getProductRating());
                productResponse.setProductImageUrl("http://localhost:8080/vnfbasket/getProductsImageByProductId/"+product.getProductId());
                return productResponse;
            }).toList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ProductResponseDTO getProductByProductName(String productName) {
    try {
        Products product = productsRepo.findByProductName(productName);
        ProductResponseDTO productResponse = new ProductResponseDTO();
        productResponse.setProductId(product.getProductId());
        productResponse.setProductName(product.getProductName());
        productResponse.setProductDescription(product.getProductDescription());
        productResponse.setProductPrice(product.getProductPrice());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setProductRating(product.getProductRating());
        productResponse.setProductImageUrl("http://localhost:8080/vnfbasket/getProductsImageByProductId/"+product.getProductId());
        return productResponse;
    } catch (Exception e) {
        return null;
    }
    }
    
}
