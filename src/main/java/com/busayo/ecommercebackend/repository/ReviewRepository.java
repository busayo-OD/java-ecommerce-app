package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByStatus(String status, Pageable pageable);
}
