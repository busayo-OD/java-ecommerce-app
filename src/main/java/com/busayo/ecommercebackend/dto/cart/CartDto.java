package com.busayo.ecommercebackend.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartDto {

    private Long id;
    private Long productId;
    private int quantity;
}
