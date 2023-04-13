package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.coupon.CouponDto;
import com.busayo.ecommercebackend.model.Coupon;

import java.util.List;

public interface CouponService {
    Boolean addCoupon(CouponDto couponDto);
    Coupon getCoupon(Long couponId);
    List<Coupon> getAllCoupon();
    Boolean updateCoupon(CouponDto couponDto, Long couponId);
    Boolean deleteCoupon(Long couponId);
}
