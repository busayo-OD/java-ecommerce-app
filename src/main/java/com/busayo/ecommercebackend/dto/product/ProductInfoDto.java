package com.busayo.ecommercebackend.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoDto {
    private Long id;
    private String name;
    private  String image;
    private double price;
}
