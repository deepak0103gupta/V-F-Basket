package com.V.FBasket.VnFBasket.dto;

import com.V.FBasket.VnFBasket.constants.OrderStatus;
import com.V.FBasket.VnFBasket.constants.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponseDTO {
    private Long orderId;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private Double totalAmount;
    private LocalDateTime orderDate;

    public OrderResponseDTO(Long orderId, OrderStatus orderStatus, PaymentStatus paymentStatus, Double totalAmount, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
    }
}