package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.repository.OrderDetailsRepository;
import com.busayo.ecommercebackend.service.OrderDetailsService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private OrderDetailsRepository orderDetailsRepository;

    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
    }
}
