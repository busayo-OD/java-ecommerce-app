package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> deleteByUserId(Long userId);
    List<Cart> findByUserId(Long userId);
}
