package com.busayo.ecommercebackend.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddToCartDto {
    private Long id;
    private Long productId;
    private int quantity;
}
