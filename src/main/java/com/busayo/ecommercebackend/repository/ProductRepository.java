package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategoryIdAndStatus(Long categoryId, String status, Pageable pageable);
    Page<Product> findByProductTypeIdAndStatus(Long productTypeId, String status, Pageable pageable);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByProductTypeId(Long productTypeId);
    Page<Product> findByStatus(String status, Pageable pageable);
    Product findByName(String name);
}
