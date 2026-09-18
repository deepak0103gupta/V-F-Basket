package com.V.FBasket.VnFBasket.service;

import com.V.FBasket.VnFBasket.model.Payments;

public interface PaymentsService {
    public Payments initiatePayment(Long orderId, String paymentType);
    public void handlePaymentSuccess(Long orderId, String transactionId);
    public void handlePaymentFailure(Long orderId);
}
