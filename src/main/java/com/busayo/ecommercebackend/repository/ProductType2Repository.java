package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.dto.productType.ProductTypeNoOfProductsDto;

import java.util.List;

public interface ProductType2Repository {
    List<ProductTypeNoOfProductsDto> productTypeNoOfEachProduct();
}
