package com.V.FBasket.VnFBasket.serviceImpl;

import com.V.FBasket.VnFBasket.dto.AddToCartRequestDTO;
import com.V.FBasket.VnFBasket.dto.CartItemResponseDTO;
import com.V.FBasket.VnFBasket.dto.CartResponseDTO;
import com.V.FBasket.VnFBasket.dao.CartItemRepository;
import com.V.FBasket.VnFBasket.dao.CartRepository;
import com.V.FBasket.VnFBasket.dao.ProductsRepository;
import com.V.FBasket.VnFBasket.dao.UserRepository;
import com.V.FBasket.VnFBasket.model.Cart;
import com.V.FBasket.VnFBasket.model.CartItem;
import com.V.FBasket.VnFBasket.model.Products;
import com.V.FBasket.VnFBasket.model.User;
import com.V.FBasket.VnFBasket.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductsRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public void addToCart(Long userId, AddToCartRequestDTO addToCartRequestDTO) {
        Cart cart = cartRepository.findByUserUserId(userId)
                .orElseGet(() -> createCart(userId));

        Products product = productRepository.findById(addToCartRequestDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByCartCartIdAndProductProductId(cart.getCartId(), addToCartRequestDTO.getProductId())
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + addToCartRequestDTO.getQuantity());
            cartItem.setTotalPrice(product.getProductPrice() * cartItem.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(addToCartRequestDTO.getQuantity());
            newItem.setTotalPrice(product.getProductPrice() * addToCartRequestDTO.getQuantity());
            cart.getCartItems().add(newItem);
        }

        cartRepository.save(cart);

    }

    @Override
    public void updateCartItem(Long userId, Long productId, Integer quantity) {
        CartItem cartItem = cartItemRepository
                .findByCartUserUserIdAndProductProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getProduct().getProductPrice() * quantity);

    }

    @Override
    public void removeFromCart(Long userId, Long productId) {
        cartItemRepository.deleteByCartUserUserIdAndProductProductId(userId, productId);

    }

    @Override
    public void clearCart(Long userId) {
        cartItemRepository.deleteAllByCartUserUserId(userId);

    }

    @Override
    @Transactional(readOnly = true)
    public CartResponseDTO getCart(Long userId) {

        Cart cart = cartRepository.findByUserUserId(userId)
                .orElse(null);

        // If cart does not exist → return empty cart response
        if (cart == null) {
            return new CartResponseDTO(
                    null,                     // no cart yet
                    new ArrayList<>(),         // empty items
                    0.0                       // total = 0
            );
        }

        List<CartItemResponseDTO> items = cart.getCartItems()
                .stream()
                .map(item -> new CartItemResponseDTO(
                        item.getProduct().getProductId(),
                        item.getProduct().getProductName(),
                        item.getProduct().getProductPrice(),
                        item.getQuantity(),
                        item.getTotalPrice()
                ))
                .toList();

        Double total = items.stream()
                .mapToDouble(CartItemResponseDTO::getTotalPrice)
                .sum();

        return new CartResponseDTO(
                cart.getCartId(),
                items,
                total
        );
    }

    private Cart createCart(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = new Cart();
        cart.setUser(user);

        return cartRepository.save(cart);
    }
}
