package com.busayo.ecommercebackend.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private double price;
    private List<ProductImageDto> images;
    private int stock;
    private Long brandId;
    private Long categoryId;
    private String colour;

}
