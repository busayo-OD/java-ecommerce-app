package com.busayo.ecommercebackend.utils;

import com.busayo.ecommercebackend.model.enums.CouponStatus;
import com.busayo.ecommercebackend.model.enums.CouponType;

public class CouponUtil {
    public static final CouponStatus getCouponStatus (String couponStatus ) {

        switch (couponStatus.toUpperCase()) {
            case "ENABLED":
                return CouponStatus.ENABLED;

            case "PLANNED":
                return CouponStatus.PLANNED;

            case "FINISHED":
                return CouponStatus.FINISHED;

            default:
                return null;
        }
    }

    public static final CouponType getCouponType (String couponType ) {

        switch (couponType.toUpperCase()) {
            case "PERCENTAGE":
                return CouponType.PERCENTAGE;

            case "FIXED AMOUNT":
                return CouponType.FIXED_AMOUNT;

            default:
                return null;
        }
    }
}
