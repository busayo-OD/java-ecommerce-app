package com.busayo.ecommercebackend.dto.productType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductTypeNoOfProductsDto {

    private String name;
    private Integer stock;

}
