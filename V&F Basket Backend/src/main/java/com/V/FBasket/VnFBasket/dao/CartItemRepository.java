package com.V.FBasket.VnFBasket.dao;

import com.V.FBasket.VnFBasket.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository  extends JpaRepository<CartItem,Long> {

    @Query("""
       SELECT ci
       FROM CartItem ci
       JOIN FETCH ci.product
       WHERE ci.cart.user.userId = :userId
       AND ci.product.productId = :productId
       """)
    Optional<CartItem> findByCartUserUserIdAndProductProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    @Modifying
    @Query("""
       DELETE FROM CartItem ci
       WHERE ci.cart.user.userId = :userId
       AND ci.product.productId = :productId
       """)
    void deleteByCartUserUserIdAndProductProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    @Modifying
    @Query("""
       DELETE FROM CartItem ci
       WHERE ci.cart.user.userId = :userId
       """)
    void deleteAllByCartUserUserId(@Param("userId") Long userId);


    @Query("""
       SELECT ci
       FROM CartItem ci
       JOIN FETCH ci.product
       WHERE ci.cart.cartId = :cartId
       AND ci.product.productId = :productId
       """)
    Optional<CartItem> findByCartCartIdAndProductProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);

    @Modifying
    @Query("""
       DELETE FROM CartItem ci
       WHERE ci.cart.cartId = :cartId
       """)
    void deleteAllByCartCartId(@Param("cartId") Long cartId);

}
