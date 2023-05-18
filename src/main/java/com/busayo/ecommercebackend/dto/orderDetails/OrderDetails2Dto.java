package com.busayo.ecommercebackend.dto.orderDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class OrderDetails2Dto {

    private List<OrderDetailsDto> orderItems;
    private double total;
}
