package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.brand.BrandDto;
import com.busayo.ecommercebackend.dto.brand.BrandInfoDto;
import com.busayo.ecommercebackend.exception.BrandNotFoundException;
import com.busayo.ecommercebackend.model.Brand;
import com.busayo.ecommercebackend.repository.BrandRepository;
import com.busayo.ecommercebackend.repository.CategoryRepository;
import com.busayo.ecommercebackend.service.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public BrandServiceImpl(BrandRepository brandRepository,
                            CategoryRepository categoryRepository,
                            ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addBrand(BrandDto brandDto) {
        Brand brand = new Brand();
        brand.setName(brandDto.getName().toUpperCase());
        brand.setStatus("Active");
        brand.setLogo(brandDto.getLogo());
        brandRepository.save(brand);
    }

    @Override
    public Brand getBrand(Long brandId) {
        Brand brand;
        brand = brandRepository.findById(brandId)

                .orElseThrow(() -> new BrandNotFoundException(brandId));

        return brand;
    }

    private final List<Brand> brands = new ArrayList<>();

    @Override
    public List<BrandInfoDto> getActiveBrands() {
        brands.clear();
        List<Brand> allBrands = brandRepository.findAll();

        for (Brand brand : allBrands) {
            if (brand.getStatus().trim().equals("Active")) {
                brands.add(brand);
            }}

        return brands.stream().map((brand1) -> modelMapper.map(brand1, BrandInfoDto.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<Brand> getAllBrands(){
//        brands.clear();
//        try {
//            brands.addAll(brandRepository.findAll());
//            return brands;
//        }catch (NullPointerException e){
//            return brands;
//        }
//    }

    @Override
    public Boolean updateBrand(BrandDto brandDto, Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));

        brand.setId(brandId);
        brand.setName(brandDto.getName());
        brandRepository.save(brand);
        return true;
    }

    @Override
    public void deleteBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new BrandNotFoundException(brandId));

        brand.setStatus("Deleted");
        brandRepository.save(brand);
    }
}
