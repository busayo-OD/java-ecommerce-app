package com.busayo.ecommercebackend.dto.orderDetails;

import com.busayo.ecommercebackend.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {

    private Long id;
    private Order order;
    private Long productId;
    private String productName;
    private double total;
    private int quantity;

}
