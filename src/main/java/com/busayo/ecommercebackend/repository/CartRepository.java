package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
