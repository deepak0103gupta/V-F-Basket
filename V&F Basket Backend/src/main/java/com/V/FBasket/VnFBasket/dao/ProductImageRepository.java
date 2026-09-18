package com.V.FBasket.VnFBasket.dao;

import com.V.FBasket.VnFBasket.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {

    Optional<ProductImage> findByProductProductId(Long productId);
}
