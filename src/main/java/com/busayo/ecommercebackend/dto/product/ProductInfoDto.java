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
public class ProductInfoDto {
    private Long id;
    private String name;
    private double price;
    private String description;
    private List<ProductImageDto> images;
    private int stock;
    private String brand;
    private String category;
    private String colour;
}
