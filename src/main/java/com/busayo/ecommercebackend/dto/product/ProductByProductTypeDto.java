package com.busayo.ecommercebackend.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductByProductTypeDto {

    private Long id;
    private String name;
    private String description;
    private double price;
    private List<ProductImageDto> images;
    private int stock;
    private String brand;
    private String category;
}
