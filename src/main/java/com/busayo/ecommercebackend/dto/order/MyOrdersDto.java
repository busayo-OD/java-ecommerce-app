package com.busayo.ecommercebackend.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class MyOrdersDto {
    private Long id;
    private int orderNumber;
    private double amount;
    private String orderStatus;
    private String paymentStatus;
    private Date orderDate;
}
