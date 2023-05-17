package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.cart.AddToCartDto;
import com.busayo.ecommercebackend.dto.cart.CartDto;

public interface CartService {

    void addProductToCart(AddToCartDto addToCartDto, Long userId);
    CartDto getMyCart(Long userId);
    void deleteUserCartItems(Long userId);
    void deleteCartItem(Long id, Long userId);
}
