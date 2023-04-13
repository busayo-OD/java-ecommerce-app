package com.busayo.ecommercebackend.dto.productType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductTypeDto {
    private Long id;
    private String name;
    private String description;
}
