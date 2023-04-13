package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.brand.BrandDto;
import com.busayo.ecommercebackend.dto.brand.BrandInfoDto;
import com.busayo.ecommercebackend.model.Brand;
import com.busayo.ecommercebackend.service.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Brand> addBrand(@RequestBody BrandDto brandDto){
        Brand savedBrand = brandService.addBrand(brandDto);
        return new ResponseEntity<>(savedBrand, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Brand> getBrand(@PathVariable("id") Long brandId){
        Brand brandDto = brandService.getBrand(brandId);
        return  ResponseEntity.ok(brandDto);
    }

    @GetMapping
    public ResponseEntity<List<BrandInfoDto>> getActiveBrands(){
        return ResponseEntity.ok(brandService.getActiveBrands());
    }

//    @GetMapping
//    public ResponseEntity<List<Brand>> getAllBrands(){
//        return ResponseEntity.ok(brandService.getAllBrands());
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Boolean> updateBrand(@RequestBody BrandDto brandDto,
                                                  @PathVariable("id") Long brandId){
        return ResponseEntity.ok(brandService.updateBrand(brandDto, brandId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteBrand(@PathVariable Long id) {

        return ResponseEntity.ok(brandService.deleteBrand(id));
    }

}
