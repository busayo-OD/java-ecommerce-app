package com.busayo.ecommercebackend.dto.order;

import com.busayo.ecommercebackend.dto.cart.CartItemDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PlaceOrderDto {

    private String firstname;
    private String lastname;
    private String email;
    private String streetAddress;
    private String state;
    private String city;
    private String phoneNumber;
    private String zipCode;
    private Long shippingMethodId;
    private String couponCode;
    private List<CartItemDto> cartItems;
    private double subtotal;
}
