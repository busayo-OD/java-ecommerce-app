package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.product.ProductDto;
import com.busayo.ecommercebackend.dto.product.*;
import com.busayo.ecommercebackend.service.ProductService;
import com.busayo.ecommercebackend.utils.AppConstants;
import com.busayo.ecommercebackend.utils.AppConstants2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public boolean addProduct(@RequestBody ProductDto productDto) {

        return productService.addProduct(productDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductInfoDto> getProduct(@PathVariable("id") Long productId){
        ProductInfoDto product = productService.getProduct(productId);
        return  ResponseEntity.ok(product);
    }

    @GetMapping()
    public ProductResponse2Dto getProductsWithPaginationAndSorting(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return productService.getProductsWithPaginationAndSorting(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/category/admin/{id}")
    public ProductResponse3Dto getProductsByCategoryWithPaginationAndSorting(
            @PathVariable("id") Long categoryId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return productService.getProductsByCategoryIdWithPaginationAndSorting(categoryId, pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/customers/category/{id}")
    public ProductResponse2Dto customerPageGetProductByCategory(
            @PathVariable("id") Long categoryId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return productService.customerPageGetProductByCategory(categoryId, pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/filter-products")
    public ProductResponse2Dto getFilteredProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants2.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants2.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants2.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants2.DEFAULT_SORT_DIRECTION, required = false) String sortDir)
    {

        return productService.filterProductsByCategoryAndBrand(categoryId,brandId, pageNo, pageSize, sortBy, sortDir);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public boolean updateProduct(@RequestBody ProductDto productDto,
                                 @PathVariable("id") Long productIdId){
        return productService.updateProduct(productDto, productIdId);
    }

    @GetMapping("/search")
    public ProductResponse2Dto searchProductsByNameOrDescription (@RequestParam("query") String query,
                                                                  @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
                                                                  @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                                  @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                                  @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return productService.searchProducts(query,pageNo, pageSize, sortBy, sortDir);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public boolean deleteProduct(@PathVariable Long id) {

        return productService.deleteProduct(id);
    }

    @GetMapping("/best-selling")
    public ProductResponse5Dto getBestSellingProducts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize
    ){
        return productService.getProductsByTotalQuantityDescending(pageNo, pageSize);
    }

    @GetMapping("/reviewed")
    public ProductResponse5Dto getReviewedProducts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return productService.getReviewedProducts(pageNo, pageSize, sortBy, sortDir);
    }

}
