package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.productType.ProductTypeDto;
import com.busayo.ecommercebackend.dto.productType.ProductTypeNoOfProductsDto;
import com.busayo.ecommercebackend.model.ProductType;
import com.busayo.ecommercebackend.service.ProductTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-types")
public class ProductTypeController {

    private  final ProductTypeService productTypeService;

    public ProductTypeController(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Boolean addProductType(@RequestBody ProductTypeDto productTypeDto){
        return productTypeService.addProductType(productTypeDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductType> getProductType(@PathVariable("id") Long productTypeId){
        ProductType productType = productTypeService.getProductType(productTypeId);
        return  ResponseEntity.ok(productType);
    }

    @GetMapping
    public ResponseEntity<List<ProductType>> getAllProductTypes(){
        return ResponseEntity.ok(productTypeService.getAllProductTypes());
    }

    @GetMapping("/productsnos")
    public List<ProductTypeNoOfProductsDto> getProductTypeNoOfProducts(){
        List<ProductTypeNoOfProductsDto> productTypeNoOfProductsDtos = productTypeService.getProductTypeNoOfEachProduct();
        return  productTypeNoOfProductsDtos;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Boolean> updateProductType(@RequestBody ProductTypeDto productTypeDto,
                                               @PathVariable("id") Long productTypeId){
        return ResponseEntity.ok(productTypeService.updateProductType(productTypeDto, productTypeId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteProductType(@PathVariable Long id) {

        return ResponseEntity.ok(productTypeService.deleteProductType(id));
    }

}
