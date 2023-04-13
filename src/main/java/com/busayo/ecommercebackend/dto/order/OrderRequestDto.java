package com.busayo.ecommercebackend.dto.order;

import com.busayo.ecommercebackend.model.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    private Order order;
}
