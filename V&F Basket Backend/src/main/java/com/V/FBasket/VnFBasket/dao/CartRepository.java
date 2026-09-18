package com.V.FBasket.VnFBasket.dao;

import com.V.FBasket.VnFBasket.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("""
       SELECT c
       FROM Cart c
       LEFT JOIN FETCH c.cartItems ci
       LEFT JOIN FETCH ci.product
       WHERE c.user.userId = :userId
       """)
    Optional<Cart> findByUserUserId(@Param("userId") Long userId);
}
