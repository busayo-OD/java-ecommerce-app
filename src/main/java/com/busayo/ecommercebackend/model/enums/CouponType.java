package com.busayo.ecommercebackend.model.enums;

import lombok.Getter;


@Getter

public enum CouponType {

    PERCENTAGE("Percentage"),
    FIXED_AMOUNT("Fixed Amount");

    private final String value;

    CouponType(String value) {
        this.value = value;
    }

    }
