package com.busayo.ecommercebackend.dto.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderReviewDto {
    private int noOfItems;
    private double subtotal;
    private double discount;
    private double shipping;
    private double vat;
    private double total;
}
