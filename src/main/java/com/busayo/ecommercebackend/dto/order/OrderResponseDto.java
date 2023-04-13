package com.busayo.ecommercebackend.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
    private int orderNumber;
    private String status;
    private String message;
}
