package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.product.*;

import java.util.List;

public interface ProductService {
    Boolean addProduct(ProductDto productDto);

    ProductDto getProduct(Long productId);

    ProductResponse2Dto getProductsWithPaginationAndSorting(String status,int pageNo, int pageSize, String sortBy, String sortDir);

    List<ProductListDto> getAllProducts();

    ProductResponse3Dto getProductsByCategoryIdWithPaginationAndSorting(Long categoryId, String status, int pageNo, int pageSize, String sortBy, String sortDir);

    List<ProductByCategoryDto> getProductsByCategoryId(Long categoryId);

    ProductResponse4Dto getProductsByProductTypeIdWithPaginationAndSorting(Long productTypeId, String status, int pageNo, int pageSize, String sortBy, String sortDir);

    List<ProductByProductTypeDto> getProductsByProductTypeId(Long productTypeId);

    Boolean updateProduct(ProductDto productDto, Long productId);

    Boolean deleteProduct(Long productId);

    ProductResponse2Dto searchProducts(String query, int pageNo, int pageSize, String sortBy, String sortDir);

}
