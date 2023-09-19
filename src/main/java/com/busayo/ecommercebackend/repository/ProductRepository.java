package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategoryIdAndStatus(Long categoryId, String status, Pageable pageable);
    Page<Product> findByCategoryIdAndProductTypeIdAndStatus(Long categoryId, Long productTypeId, String status, Pageable pageable);
    Page<Product> findByProductTypeIdAndStatus(Long productTypeId, String status, Pageable pageable);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByProductTypeId(Long productTypeId);
    Page<Product> findByStatus(String status, Pageable pageable);
    Page<Product> findByCategoryIdAndProductTypeIdAndBrandIdAndStatus(Long categoryId,Long productTypeId, Long brandId, String status, Pageable pageable);
    Product findByName(String name);

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
            "AND (:productTypeId IS NULL OR p.product_type_id = :productTypeId) " +
            "AND (:brandId IS NULL OR p.brand_id = :brandId) " +
            "AND p.status = 'Active'",
            countQuery = "SELECT COUNT(*) FROM products p " +
                    "WHERE (:categoryId IS NULL OR p.category_id = :categoryId) " +
                    "AND (:productTypeId IS NULL OR p.product_type_id = :productTypeId) " +
                    "AND (:brandId IS NULL OR p.brand_id = :brandId) " +
                    "AND p.status = 'Active'",
            nativeQuery = true)
    Page<Product> filterProductsByCategoryAndTypeAndBrand(
            @Param("categoryId") Long categoryId,
            @Param("productTypeId") Long productTypeId,
            @Param("brandId") Long brandId,
            Pageable pageable);

}
