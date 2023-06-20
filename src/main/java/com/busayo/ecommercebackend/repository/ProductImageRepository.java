package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    ProductImage findByImage(String image);
}
