package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetail, Long> {
}
