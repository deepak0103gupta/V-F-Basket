package com.V.FBasket.VnFBasket.dao;

import com.V.FBasket.VnFBasket.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {
}
