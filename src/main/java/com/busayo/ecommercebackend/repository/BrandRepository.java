package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByName(String name);
    Brand findByName(String name);
}
