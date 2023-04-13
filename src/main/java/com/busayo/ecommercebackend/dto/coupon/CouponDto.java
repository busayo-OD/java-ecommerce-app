package com.busayo.ecommercebackend.dto.coupon;

import com.busayo.ecommercebackend.annotation.CouponStatusAnnotation;
import com.busayo.ecommercebackend.annotation.CouponTypeAnnotation;
import com.busayo.ecommercebackend.model.enums.CouponStatus;
import com.busayo.ecommercebackend.model.enums.CouponType;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CouponDto {

    private Long id;

    private String couponCode;

    @CouponStatusAnnotation(enumClass = CouponStatus.class, message = "{coupon.couponStatus.enum}")
    private String couponStatus;

    private Date startDate;

    private Date endDate;

    private double discountValue;

    @CouponTypeAnnotation(enumClass = CouponType.class, message = "{coupon.couponType.enum}")
    private String type;
}
