package com.V.FBasket.VnFBasket.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Long orderId;
    private String paymentType;
    private String transactionId;
}
