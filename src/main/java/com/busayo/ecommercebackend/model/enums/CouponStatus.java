package com.busayo.ecommercebackend.model.enums;

import lombok.Getter;

@Getter
public enum CouponStatus {

    ENABLED("Enabled"),
    PLANNED("Planned"),
    FINISHED("Finished");

    private final String value;

    CouponStatus(String value) {
        this.value = value;
    }

}