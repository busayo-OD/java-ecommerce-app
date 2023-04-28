package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}
