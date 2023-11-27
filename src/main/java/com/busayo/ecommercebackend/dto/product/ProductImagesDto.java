package com.busayo.ecommercebackend.dto.product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductImagesDto {
    private List<ProductImageDto> images;
}
