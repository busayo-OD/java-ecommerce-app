package com.busayo.ecommercebackend.dto.cart;

import com.busayo.ecommercebackend.model.Cart;
import com.busayo.ecommercebackend.model.Product;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemDto {
    private int quantity;
    private Long productId;

    public CartItemDto() {
    }

    public CartItemDto(Cart cart) {
        this.setQuantity(cart.getQuantity());
        this.setProductId(cart.getProduct().getId());
    }
}
