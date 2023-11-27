package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.User;
import com.busayo.ecommercebackend.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser(User user);
}
