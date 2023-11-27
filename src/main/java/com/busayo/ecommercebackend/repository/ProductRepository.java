package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategoryIdAndStatus(Long categoryId, String status, Pageable pageable);
    Page<Product> findByStatus(String status, Pageable pageable);
    Page<Product> findByCategoryIdAndBrandIdAndStatus(Long categoryId, Long brandId, String status, Pageable pageable);

    @Query(value = "SELECT * FROM products p WHERE " +
            "p.status = 'Active' "+
            "AND (p.name LIKE CONCAT('%', :query, '%') " +
            "OR p.description LIKE CONCAT('%', :query, '%'))", nativeQuery = true)
    Page<Product> searchProductsSQL(String query, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "WHERE EXISTS (SELECT 1 FROM Review r WHERE r.product = p AND r.status = 'ACTIVE')")
    Page<Product> findReviewedProducts(Pageable pageable);

    @Query(value = "SELECT * FROM products p " +
            "WHERE (:categoryId IS NULL OR p.category_id = :categoryId) " +
            "AND (:brandId IS NULL OR p.brand_id = :brandId) " +
            "AND p.status = 'Active'",
            countQuery = "SELECT COUNT(*) FROM products p " +
                    "WHERE (:categoryId IS NULL OR p.category_id = :categoryId) " +
                    "AND (:brandId IS NULL OR p.brand_id = :brandId) " +
                    "AND p.status = 'Active'",
            nativeQuery = true)
    Page<Product> filterProductsByCategoryAndBrand(
            @Param("categoryId") Long categoryId,
            @Param("brandId") Long brandId,
            Pageable pageable);

}
