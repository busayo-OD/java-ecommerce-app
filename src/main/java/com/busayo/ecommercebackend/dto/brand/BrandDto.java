package com.busayo.ecommercebackend.dto.brand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {

    private Long id;
    private String name;
    private String logo;
}
