package com.busayo.ecommercebackend.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class CartDto {

    private List<CartItemDto> cartItems;
    private double total;
}
