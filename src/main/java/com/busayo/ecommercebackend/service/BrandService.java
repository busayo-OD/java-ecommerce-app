package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.brand.BrandDto;
import com.busayo.ecommercebackend.dto.brand.BrandInfoDto;
import com.busayo.ecommercebackend.model.Brand;

import java.util.List;

public interface BrandService {
    Brand addBrand(BrandDto brandDto);
    Brand getBrand(Long brandId);
    List<BrandInfoDto> getActiveBrands();
//    List<Brand> getAllBrands();
    Boolean updateBrand(BrandDto brandDto, Long brandId);
    Boolean deleteBrand(Long id);
}
