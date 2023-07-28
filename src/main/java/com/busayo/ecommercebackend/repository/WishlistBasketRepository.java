package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.WishlistBasket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistBasketRepository extends JpaRepository<WishlistBasket, Long> {
    boolean existsByName(String name);
}
