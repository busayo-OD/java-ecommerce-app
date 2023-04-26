package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.order.OrderListDto;
import com.busayo.ecommercebackend.dto.order.OrderListResponseDto;
import com.busayo.ecommercebackend.dto.order.OrderRequestDto;
import com.busayo.ecommercebackend.dto.order.OrderResponseDto;

import java.util.List;

public interface OrderService {
    OrderListResponseDto getOrdersWithPaginationAndSorting(String status, int pageNo, int pageSize, String sortBy, String sortDir);
    List<OrderListDto> getAllOrders();
}
