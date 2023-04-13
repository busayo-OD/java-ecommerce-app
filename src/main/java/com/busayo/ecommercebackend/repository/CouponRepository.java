package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
