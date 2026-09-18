package com.V.FBasket.VnFBasket.serviceImpl;

import com.V.FBasket.VnFBasket.constants.OrderStatus;
import com.V.FBasket.VnFBasket.constants.PaymentStatus;
import com.V.FBasket.VnFBasket.dao.OrderRepository;
import com.V.FBasket.VnFBasket.dao.PaymentsRepository;
import com.V.FBasket.VnFBasket.model.OrderItems;
import com.V.FBasket.VnFBasket.model.Orders;
import com.V.FBasket.VnFBasket.model.Payments;
import com.V.FBasket.VnFBasket.model.Products;
import com.V.FBasket.VnFBasket.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentsSeviceImpl implements PaymentsService {

    @Autowired
    private PaymentsRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public Payments initiatePayment(Long orderId, String paymentType) {

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payments payment = new Payments();
        payment.setOrder(order);
        payment.setPaymentType(paymentType);
        payment.setPaymentStatus(PaymentStatus.PENDING);

        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public void handlePaymentSuccess(Long orderId, String transactionId) {
        // Payment Gateway Callback
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payments payment = paymentRepository.findByOrderOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setTransactionId(transactionId);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        order.setPaymentStatus(PaymentStatus.SUCCESS);
        order.setOrderStatus(OrderStatus.PLACED);
    }

    @Override
    @Transactional
    public void handlePaymentFailure(Long orderId) {

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payments payment = paymentRepository.findByOrderOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setPaymentStatus(PaymentStatus.FAILED);

        order.setPaymentStatus(PaymentStatus.FAILED);
        order.setOrderStatus(OrderStatus.CANCELLED);

        // Restore stock
        for (OrderItems item : order.getOrderItems()) {
            Products product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
        }
    }

}
