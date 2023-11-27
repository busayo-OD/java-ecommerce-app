package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.product.*;

public interface ProductService {
    boolean addProduct(ProductDto productDto);

    ProductInfoDto getProduct(Long productId);

    ProductResponse2Dto getProductsWithPaginationAndSorting(int pageNo, int pageSize, String sortBy, String sortDir);

    ProductResponse3Dto getProductsByCategoryIdWithPaginationAndSorting(Long categoryId, int pageNo, int pageSize, String sortBy, String sortDir);

    boolean updateProduct(ProductDto productDto, Long productId);

    boolean deleteProduct(Long productId);

    ProductResponse2Dto searchProducts(String query, int pageNo, int pageSize, String sortBy, String sortDir);

    ProductResponse2Dto productSearchByCategoryAndBrand(Long categoryId, Long brandId, int pageNo, int pageSize, String sortBy, String sortDir);

    ProductResponse5Dto getProductsByTotalQuantityDescending(int pageNo, int pageSize);

    ProductResponse5Dto getReviewedProducts(int pageNo, int pageSize, String sortBy, String sortDir);

    ProductResponse2Dto customerPageGetProductByCategory(Long categoryId, int pageNo, int pageSize, String sortBy, String sortDir);

    ProductResponse2Dto filterProductsByCategoryAndBrand(Long categoryId, Long brandId, int pageNo, int pageSize, String sortBy, String sortDir);

}
