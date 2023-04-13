package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.ProductType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    boolean existsByName(String name);
    ProductType findByName(String name);
}
