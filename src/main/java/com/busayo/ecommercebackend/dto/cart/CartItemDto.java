package com.busayo.ecommercebackend.dto.cart;

import com.busayo.ecommercebackend.model.Cart;
import com.busayo.ecommercebackend.model.Product;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemDto {
    private Long id;
    private int quantity;
    private Product product;

    public CartItemDto() {
    }

    public CartItemDto(Cart cart) {
        this.setId(cart.getId());
        this.setQuantity(cart.getQuantity());
        this.setProduct(cart.getProduct());
    }
}
