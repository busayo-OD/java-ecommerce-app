package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.coupon.CouponDto;
import com.busayo.ecommercebackend.dto.coupon.CouponResponseDto;
import com.busayo.ecommercebackend.model.Coupon;

import java.util.List;

public interface CouponService {
    Boolean addCoupon(CouponDto couponDto);
    Coupon getCoupon(Long couponId);
    CouponResponseDto getCouponsWithPaginationAndSorting(int pageNo, int pageSize, String sortBy, String sortDir);
    Boolean updateCoupon(CouponDto couponDto, Long couponId);
    Boolean deleteCoupon(Long couponId);
    CouponResponseDto getCouponsByStatus(String couponStatus, int pageNo, int pageSize, String sortBy, String sortDir);
}
