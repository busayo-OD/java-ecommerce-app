package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.productType.ProductTypeDto;
import com.busayo.ecommercebackend.dto.productType.ProductTypeNoOfProductsDto;

import com.busayo.ecommercebackend.exception.ProductTypeNotFoundException;
import com.busayo.ecommercebackend.model.ProductType;
import com.busayo.ecommercebackend.repository.ProductType2Repository;
import com.busayo.ecommercebackend.repository.ProductTypeRepository;
import com.busayo.ecommercebackend.service.ProductTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ModelMapper modelMapper;
    private ProductType2Repository productType2Repository;

    public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository,
                                  ProductType2Repository productType2Repository) {
        this.productTypeRepository = productTypeRepository;
        this.productType2Repository = productType2Repository;

    }

    @Override
    public Boolean addProductType(ProductTypeDto productTypeDto) {
        boolean existingName = productTypeRepository.existsByName(productTypeDto.getName().toUpperCase());

        ProductType productType;
        if (existingName) {
            productType = productTypeRepository.findByName(productTypeDto.getName().toUpperCase());
            productType.setStatus("Active");
        } else {
            productType = new ProductType();
            productType.setName(productTypeDto.getName().toUpperCase());
            productType.setStatus("Active");
            productType.setDescription(productTypeDto.getDescription());

        }
        productTypeRepository.save(productType);
        return true;
    }

    @Override
    public ProductType getProductType(Long productTypeId) {
        ProductType productType;
        productType = productTypeRepository.findById(productTypeId)

                .orElseThrow(() -> new ProductTypeNotFoundException(productTypeId));

        return productType;
    }

    private List<ProductType> productTypes = new ArrayList<>();

    @Override
    public List<ProductType> getAllProductTypes() {

        productTypes.clear();
        List<ProductType> allProductTypes = productTypeRepository.findAll();

        for (ProductType productType : allProductTypes) {
            if ((allProductTypes != null) && (productType.getStatus().trim().equals("Active"))) {
                productTypes.add(productType);
            }
        }
        return productTypes;
    }

//    @Override
//    public List<ProductTypeNoOfProductsDto> getProductTypeNoOfEachProduct() {
//        return productSearchRepository.productTypeNoOfEachProduct();
//    };

    @Override
    public Boolean updateProductType(ProductTypeDto productTypeDto, Long productTypeId) {
        ProductType productType = productTypeRepository.findById(productTypeId)
                .orElseThrow(() -> new ProductTypeNotFoundException(productTypeId));

        productType.setId(productTypeId);
        productType.setName(productTypeDto.getName().toUpperCase());
        productType.setDescription(productTypeDto.getDescription());
        productTypeRepository.save(productType);
        return true;
    }

    @Override
    public void deleteProductType(Long productTypeId) {
        ProductType productType = productTypeRepository.findById(productTypeId)
                .orElseThrow(() -> new ProductTypeNotFoundException(productTypeId));

        productType.setStatus("Deleted");
        productTypeRepository.save(productType);
    }

    @Override
    public List<ProductTypeNoOfProductsDto> getProductTypeNoOfEachProduct() {

        return productType2Repository.productTypeNoOfEachProduct();
    }
}
