package com.busayo.ecommercebackend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderListDto {

    private Long id;
    private int orderNumber;
    private double amount;
    private String customerName;
    private String orderStatus;
    private String paymentStatus;
    private Date orderDate;
}
