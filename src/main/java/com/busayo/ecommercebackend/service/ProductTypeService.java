package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.productType.ProductTypeDto;
import com.busayo.ecommercebackend.dto.productType.ProductTypeNoOfProductsDto;
import com.busayo.ecommercebackend.model.ProductType;

import java.util.List;

public interface ProductTypeService {
    Boolean addProductType(ProductTypeDto productTypeDto);
    ProductType getProductType(Long productTypeId);
    List<ProductType> getAllProductTypes();
    Boolean updateProductType(ProductTypeDto productTypeDto, Long productTypeId);
    void deleteProductType(Long productTypeId);
    List<ProductTypeNoOfProductsDto> getProductTypeNoOfEachProduct();
}
