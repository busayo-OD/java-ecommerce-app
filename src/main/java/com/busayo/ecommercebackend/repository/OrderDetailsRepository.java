package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.OrderDetail;
import com.busayo.ecommercebackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long orderId);

    @Query("SELECT p, SUM(od.quantity) as totalQuantity " +
            "FROM OrderDetail od " +
            "JOIN od.product p " +
            "GROUP BY p " +
            "ORDER BY totalQuantity DESC")
    Page<Product> findProductQuantitiesSumOrderByDescendingQuantity(Pageable pageable);
}
