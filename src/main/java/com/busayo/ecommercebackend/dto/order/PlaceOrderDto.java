package com.busayo.ecommercebackend.dto.order;

import lombok.Getter;
import lombok.Setter;

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
}
