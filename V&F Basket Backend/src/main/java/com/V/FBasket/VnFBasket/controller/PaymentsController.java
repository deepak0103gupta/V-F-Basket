package com.V.FBasket.VnFBasket.controller;

import com.V.FBasket.VnFBasket.dto.PaymentRequestDTO;
import com.V.FBasket.VnFBasket.dto.PaymentWebhookDTO;
import com.V.FBasket.VnFBasket.model.Payments;
import com.V.FBasket.VnFBasket.serviceImpl.PaymentsSeviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vnfbasket")
public class PaymentsController {

    @Autowired
    private PaymentsSeviceImpl paymentsSeviceImpl;

    /**
     * Initiate Payment
     * POST
     */
    @PostMapping("/initiatePayment")
    public ResponseEntity<Payments> initiatePayment(@RequestBody PaymentRequestDTO paymentRequest) {

        Payments payment = paymentsSeviceImpl.initiatePayment(paymentRequest.getOrderId(), paymentRequest.getPaymentType());
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    /**
     * Payment Success Callback
     * POST
     */
    @PostMapping("/handlePaymentSuccess/{orderId}")
    public ResponseEntity<String> handlePaymentSuccess(
            @PathVariable Long orderId,
            @RequestParam String transactionId) {

        paymentsSeviceImpl.handlePaymentSuccess(orderId, transactionId);
        return ResponseEntity.ok("Payment successful and order placed");
    }

    /**
     * Payment Failure Callback
     * POST
     */
    @PostMapping("/handlePaymentFailure/{orderId}")
    public ResponseEntity<String> handlePaymentFailure(
            @PathVariable Long orderId) {

        paymentsSeviceImpl.handlePaymentFailure(orderId);
        return ResponseEntity.ok("Payment failed and order cancelled");
    }

    /**
     * Step 2: Webhook Endpoint
     * Called automatically by Payment Gateway
     *
     * POST /api/payments/webhook
     */
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody PaymentWebhookDTO webhookDTO) {

        // ⚠ In real production: verify signature here

        if ("SUCCESS".equalsIgnoreCase(webhookDTO.getStatus())) {

            paymentsSeviceImpl.handlePaymentSuccess(
                    webhookDTO.getOrderId(),
                    webhookDTO.getTransactionId()
            );

        } else if ("FAILED".equalsIgnoreCase(webhookDTO.getStatus())) {

            paymentsSeviceImpl.handlePaymentFailure(
                    webhookDTO.getOrderId()
            );
        }

        return ResponseEntity.ok("Webhook processed successfully");
    }
}
