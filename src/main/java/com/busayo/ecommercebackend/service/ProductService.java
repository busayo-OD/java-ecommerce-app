package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.product.*;

import java.util.List;

public interface ProductService {
    Boolean addProduct(ProductDto productDto);

    ProductDto getProduct(Long productId);

    ProductResponse2Dto getAllProductsWithPaginationAndSorting(int pageNo, int pageSize, String sortBy, String sortDir);

    List<ProductListDto> getAllProducts();

    ProductResponse3Dto getProductsByCategoryIdWithPaginationAndSorting(Long categoryId, int pageNo, int pageSize, String sortBy, String sortDir);

    List<ProductByCategoryDto> getProductsByCategoryId(Long categoryId);

    ProductResponse4Dto getProductsByProductTypeIdWithPaginationAndSorting(Long productTypeId, int pageNo, int pageSize, String sortBy, String sortDir);

    List<ProductByProductTypeDto> getProductsByProductTypeId(Long productTypeId);

//    ProductResponse1Dto getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir);

    Boolean updateProduct(ProductDto productDto, Long productId);

    Boolean deleteProduct(Long productId);



}
