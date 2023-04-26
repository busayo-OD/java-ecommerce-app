package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.product.ProductDto;
import com.busayo.ecommercebackend.dto.product.*;
import com.busayo.ecommercebackend.service.ProductService;
import com.busayo.ecommercebackend.utils.AppConstants;
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
    public Boolean addProduct(@RequestBody ProductDto productDto) {
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

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductByCategoryDto>> getProductsByCategory(@PathVariable("id") Long categoryId){
        return ResponseEntity.ok(productService.getProductsByCategoryId(categoryId));
    }

    @GetMapping("/product-type/pagination/{id}/{status}")
    public ProductResponse4Dto getProductsByProductTypeWithPaginationAndSorting(
            @PathVariable("id") Long productTypeId,
            @PathVariable String status,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return productService.getProductsByProductTypeIdWithPaginationAndSorting(productTypeId, status, pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/product-type/{id}")
    public ResponseEntity<List<ProductByProductTypeDto>> getProductsByProductType(@PathVariable("id") Long productTypeId){
        return ResponseEntity.ok(productService.getProductsByProductTypeId(productTypeId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Boolean> updateProduct(@RequestBody ProductDto productDto,
                                                 @PathVariable("id") Long productIdId){
        return ResponseEntity.ok(productService.updateProduct(productDto, productIdId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long id) {

        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
