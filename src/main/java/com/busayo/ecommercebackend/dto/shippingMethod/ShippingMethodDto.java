package com.busayo.ecommercebackend.dto.shippingMethod;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShippingMethodDto {
    private Long id;
    private String name;
    private String description;
    private String timeRange;
    private double price;
}
