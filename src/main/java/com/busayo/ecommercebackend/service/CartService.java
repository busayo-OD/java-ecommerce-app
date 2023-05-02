package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.cart.CartDto;
import com.busayo.ecommercebackend.dto.cart.MyCartDto;

import java.util.List;

public interface CartService {

    Boolean addProductToCart(CartDto cartDto, Long id);
    List<MyCartDto> getMyCart(Long id);
}
