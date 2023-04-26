package com.busayo.ecommercebackend.dto.coupon;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CouponResponseDto {

    private List<CouponDto> content;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean last;
}
