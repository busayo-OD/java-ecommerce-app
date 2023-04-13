package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
