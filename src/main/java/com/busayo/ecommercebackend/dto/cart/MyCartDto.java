package com.busayo.ecommercebackend.dto.cart;

import com.busayo.ecommercebackend.model.Product;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyCartDto {

    private Long id;

    private Product product;
}
