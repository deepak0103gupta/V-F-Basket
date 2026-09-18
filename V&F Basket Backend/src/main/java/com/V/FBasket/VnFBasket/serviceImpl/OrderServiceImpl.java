package com.V.FBasket.VnFBasket.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.V.FBasket.VnFBasket.constants.OrderStatus;
import com.V.FBasket.VnFBasket.constants.PaymentStatus;
import com.V.FBasket.VnFBasket.dao.*;
import com.V.FBasket.VnFBasket.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.V.FBasket.VnFBasket.service.OrderService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Orders createOrder(Long userId, Long addressId) {

        Cart cart = cartRepository.findByUserUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Orders order = new Orders();
        order.setUser(cart.getUser());
        order.setAddress(addressRepository.findById(addressId).orElseThrow());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.PENDING);

        List<OrderItems> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (CartItem cartItem : cart.getCartItems()) {

            Products product = cartItem.getProduct();

            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock");
            }

            // Reserve stock (deduct temporarily)
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());

            OrderItems item = new OrderItems();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(product.getProductPrice());

            totalAmount += product.getProductPrice() * cartItem.getQuantity();
            orderItems.add(item);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        Orders savedOrder = orderRepository.save(order);

        // clear cart
        cartItemRepository.deleteAllByCartCartId(cart.getCartId());

        return savedOrder;
    }

    @Override
    @Transactional(readOnly = true)
    public Orders getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Orders> getOrdersByUser(Long userId) {
        return orderRepository.findByUserUserId(userId);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Delivered order cannot be cancelled");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        for (OrderItems item : order.getOrderItems()) {
            Products product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
        }
    }
}
