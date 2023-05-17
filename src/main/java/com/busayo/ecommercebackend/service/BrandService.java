package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.brand.BrandDto;
import com.busayo.ecommercebackend.dto.brand.BrandInfoDto;
import com.busayo.ecommercebackend.model.Brand;

import java.util.List;

public interface BrandService {
    void addBrand(BrandDto brandDto);
    Brand getBrand(Long brandId);
    List<BrandInfoDto> getActiveBrands();
    Boolean updateBrand(BrandDto brandDto, Long brandId);
    void deleteBrand(Long id);
}
