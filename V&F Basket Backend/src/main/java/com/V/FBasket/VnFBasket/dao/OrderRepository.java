package com.V.FBasket.VnFBasket.dao;


import com.V.FBasket.VnFBasket.constants.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.V.FBasket.VnFBasket.model.Orders;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

//    @Query("""
//    SELECT o FROM Orders o
//    JOIN FETCH o.address
//    WHERE o.user.userId = :userId
//    """)
//    List<Orders> findByUserId(@Param("userId") Long userId);

    List<Orders> findByUserUserId(Long userId);

    boolean existsByUserUserIdAndOrderItemsProductProductIdAndOrderStatus(Long userId, Long productId, OrderStatus orderStatus);

}
