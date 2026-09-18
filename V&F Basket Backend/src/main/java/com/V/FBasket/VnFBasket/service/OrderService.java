package com.V.FBasket.VnFBasket.service;

import java.util.List;

import com.V.FBasket.VnFBasket.constants.PaymentStatus;
import com.V.FBasket.VnFBasket.dto.OrderResponseDTO;
import com.V.FBasket.VnFBasket.model.Orders;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {

    public Orders createOrder(Long userId, Long addressId);
    public Orders getOrderById(Long orderId);
    public List<Orders> getOrdersByUser(Long userId);
    public void cancelOrder(Long orderId);
}
