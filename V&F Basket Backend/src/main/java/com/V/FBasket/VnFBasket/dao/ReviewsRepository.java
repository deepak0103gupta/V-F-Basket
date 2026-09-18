package com.V.FBasket.VnFBasket.dao;

import com.V.FBasket.VnFBasket.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
    @Query("select r from Reviews r where r.user.userId = :userId")
    List<Reviews> findReviewsByUserId(@Param("userId") Long UserId);

    @Query("select r from Reviews r where r.product.productId = :productId")
    List<Reviews> findReviewsByProductId(@Param("productId") Long productId);

    @Query("select AVG(r.rating) from Reviews r where r.product.productId = :productId")
    Integer findAverageRatingByProductId(@Param("productId") Long productId);

    boolean existsByUserUserIdAndProductProductId(Long userId, Long productId);
}
