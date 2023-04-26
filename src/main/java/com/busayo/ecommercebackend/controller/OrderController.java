package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.order.OrderListDto;
import com.busayo.ecommercebackend.dto.order.OrderListResponseDto;
import com.busayo.ecommercebackend.service.OrderService;
import com.busayo.ecommercebackend.utils.AppConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/pagination/{status}")
    public OrderListResponseDto getOrdersWithPaginationAndSorting(
            @PathVariable String status,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return orderService.getOrdersWithPaginationAndSorting(status, pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping
    public ResponseEntity<List<OrderListDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}