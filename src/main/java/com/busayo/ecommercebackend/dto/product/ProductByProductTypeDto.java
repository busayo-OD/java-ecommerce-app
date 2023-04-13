package com.busayo.ecommercebackend.dto.product;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductByProductTypeDto {

    private Long id;
    private String name;
    private String description;
    private double price;
    private String image;
    private int stock;
    private String brand;
    private String category;
}
