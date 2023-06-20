package com.busayo.ecommercebackend.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductListDto {

    private Long id;
    private String name;
    private double price;
    private String brand;
    private String category;
    private String productType;
    private int stock;
    private List<ProductImageDto> images;

}
