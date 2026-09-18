package com.V.FBasket.VnFBasket.dto;

import lombok.Data;

@Data
public class PaymentWebhookDTO {
    private Long orderId;
    private String transactionId;
    private String status;
}
