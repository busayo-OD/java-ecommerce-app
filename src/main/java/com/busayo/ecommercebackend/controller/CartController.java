package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.cart.CartDto;
import com.busayo.ecommercebackend.dto.cart.MyCartDto;
import com.busayo.ecommercebackend.service.CartService;
import com.busayo.ecommercebackend.utils.CurrentUserUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public Boolean addProductToCart(@RequestBody CartDto cartDto){
        Long userId = CurrentUserUtil.getCurrentUser().getId();

        return cartService.addProductToCart(cartDto, userId);
    }

    @GetMapping("/my-cart")
    public ResponseEntity<List<MyCartDto>> getMyCart(){
        Long userId = CurrentUserUtil.getCurrentUser().getId();
        return ResponseEntity.ok(cartService.getMyCart(userId));
    }
}
