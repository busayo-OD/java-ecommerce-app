package com.busayo.ecommercebackend.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private double price;
    private String image;
    private int stock;
    private String brand;
    private String productType;
    private String category;

}
