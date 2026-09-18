package com.V.FBasket.VnFBasket.controller;

import com.V.FBasket.VnFBasket.config.UserInfoUserDetails;
import com.V.FBasket.VnFBasket.dto.AddToCartRequestDTO;
import com.V.FBasket.VnFBasket.dto.CartResponseDTO;
import com.V.FBasket.VnFBasket.serviceImpl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vnfbasket")
public class CartController {

    @Autowired
    private CartServiceImpl cartService;

    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestBody AddToCartRequestDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Long userId = user!=null ? user.getUserId() : null;

        cartService.addToCart(userId, dto);
        return new ResponseEntity<>("Item added to cart", HttpStatus.CREATED);
    }

    @PutMapping("/updateCartItem")
    public ResponseEntity<String> updateCartItem(@RequestBody AddToCartRequestDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Long userId = user!=null ? user.getUserId() : null;

        cartService.updateCartItem(userId, dto.getProductId(), dto.getQuantity());
        return ResponseEntity.ok("Cart item updated successfully");
    }

    @DeleteMapping("/removeFromCart")
    public ResponseEntity<String> removeFromCart(@RequestBody AddToCartRequestDTO dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Long userId = user!=null ? user.getUserId() : null;

        cartService.removeFromCart(userId, dto.getProductId());
        return ResponseEntity.ok("Item removed from cart");
    }

    @DeleteMapping("/clearCartItems")
    public ResponseEntity<String> clearCart() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Long userId = user!=null ? user.getUserId() : null;
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully");
    }

    @GetMapping("/getCartItems")
    public ResponseEntity<CartResponseDTO> getCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfoUserDetails user = (UserInfoUserDetails) authentication.getPrincipal();
        Long userId = user!=null ? user.getUserId() : null;
        CartResponseDTO cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }
}
