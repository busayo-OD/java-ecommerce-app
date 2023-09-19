package com.busayo.ecommercebackend.repository;

import com.busayo.ecommercebackend.model.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Page<Coupon> findByStatus(String status, Pageable pageable);
    Page<Coupon> findByCouponStatusAndStatus(String couponStatus, String status, Pageable pageable);

    @Query(value = "SELECT * FROM coupons c WHERE c.status = 'Active' AND c.coupon_code = :couponCode", nativeQuery = true)
    Coupon findByCouponCode(String couponCode);
}
