package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.cart.AddToCartDto;
import com.busayo.ecommercebackend.dto.cart.CartDto;
import com.busayo.ecommercebackend.service.CartService;
import com.busayo.ecommercebackend.utils.CurrentUserUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart-items")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public void addProductToCart(@RequestBody AddToCartDto addToCartDto){
        Long userId = CurrentUserUtil.getCurrentUser().getId();

        cartService.addProductToCart(addToCartDto, userId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-cart")
    public ResponseEntity<CartDto> getMyCart(){
        Long userId = CurrentUserUtil.getCurrentUser().getId();
        return ResponseEntity.ok(cartService.getMyCart(userId));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    public void removeItemFromCart(@PathVariable Long id) {
        Long userId = CurrentUserUtil.getCurrentUser().getId();
        cartService.deleteCartItem(id, userId);
    }
}
