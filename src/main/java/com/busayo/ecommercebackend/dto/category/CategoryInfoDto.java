package com.busayo.ecommercebackend.dto.category;

import com.busayo.ecommercebackend.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInfoDto {

    private Long id;
    private String name;
    private List<Brand> brands;
}
