package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.product.ProductDto;
import com.busayo.ecommercebackend.dto.product.*;
import com.busayo.ecommercebackend.service.ProductService;
import com.busayo.ecommercebackend.utils.AppConstants;
import com.busayo.ecommercebackend.utils.AppConstants2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long productId){
        ProductDto product = productService.getProduct(productId);
        return  ResponseEntity.ok(product);
    }

    @GetMapping("/pagination/{status}")
    public ProductResponse2Dto getProductsWithPaginationAndSorting(
            @PathVariable String status,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return productService.getProductsWithPaginationAndSorting(status,pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping
    public ResponseEntity<List<ProductListDto>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/category/pagination/{id}/{status}")
    public ProductResponse3Dto getProductsByCategoryWithPaginationAndSorting(
            @PathVariable("id") Long categoryId,
            @PathVariable String status,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return productService.getProductsByCategoryIdWithPaginationAndSorting(categoryId, status,pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/customers/category/{id}/{status}")
    public ProductResponse2Dto customerPageGetProductByCategory(
            @PathVariable("id") Long categoryId,
            @PathVariable String status,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return productService.customerPageGetProductByCategory(categoryId, status,pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/search/{category-id}/{product-type-id}/{status}")
    public ProductResponse3Dto getProductsByCategoryAndProductType(
            @PathVariable("category-id") Long categoryId,
            @PathVariable("product-type-id") Long productTypeId,
            @PathVariable String status,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return productService.getProductsByCategoryAndProductType(categoryId, productTypeId, status,pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductByCategoryDto>> getProductsByCategory(@PathVariable("id") Long categoryId){
        return ResponseEntity.ok(productService.getProductsByCategoryId(categoryId));
    }

    @GetMapping("/product-type/{product-type-id}/{status}")
    public ProductResponse2Dto getProductsByProductTypeWithPaginationAndSorting(
            @PathVariable("product-type-id") Long productTypeId,
            @PathVariable String status,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return productService.getProductsByProductType(productTypeId, status, pageNo, pageSize, sortBy, sortDir);
    }


    @GetMapping("/filter-products")
    public ProductResponse2Dto getFilteredProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long productTypeId,
            @RequestParam(required = false) Long brandId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants2.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants2.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants2.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants2.DEFAULT_SORT_DIRECTION, required = false) String sortDir)
    {

        return productService.filterProductsByCategoryAndTypeAndBrand(categoryId, productTypeId, brandId, pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/product-type/{id}")
    public ResponseEntity<List<ProductByProductTypeDto>> getProductsByProductType(@PathVariable("id") Long productTypeId){
        return ResponseEntity.ok(productService.getProductsByProductTypeId(productTypeId));
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
