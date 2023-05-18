package com.busayo.ecommercebackend.dto.orderDetails;

import com.busayo.ecommercebackend.model.Order;
import com.busayo.ecommercebackend.model.OrderDetail;
import com.busayo.ecommercebackend.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class OrderDetailsDto {

    private Long id;
    private Product product;
    private double subTotal;
    private int quantity;

    public OrderDetailsDto() {
    }

    public OrderDetailsDto(OrderDetail orderItem) {
        this.setId(orderItem.getId());
        this.setQuantity(orderItem.getQuantity());
        this.setProduct(orderItem.getProduct());
        this.setSubTotal(orderItem.getSubTotal());
    }

}
