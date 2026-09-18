package com.V.FBasket.VnFBasket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.V.FBasket.VnFBasket.model.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Long> {

    List<Products> findByCategoryCategoryId(Long categoryId);
    Products findByProductName(String productName);
    @Query("SELECT p FROM Products p WHERE p.category.categoryName = :categoryName")
    List<Products> findProductsByCategoryName(@Param("categoryName") String categoryName);
    
} 
