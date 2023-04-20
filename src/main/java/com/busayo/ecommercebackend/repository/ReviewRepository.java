package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
