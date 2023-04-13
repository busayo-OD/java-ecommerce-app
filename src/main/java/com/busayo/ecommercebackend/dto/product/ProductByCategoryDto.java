package com.busayo.ecommercebackend.dto.product;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductByCategoryDto {
    private Long id;
    private String productName;
    private String productType;
    private int stock;
}
