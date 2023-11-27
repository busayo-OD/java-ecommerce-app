package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.order.*;
import com.busayo.ecommercebackend.service.OrderService;
import com.busayo.ecommercebackend.utils.AppConstants;
import com.busayo.ecommercebackend.utils.CurrentUserUtil;
import com.busayo.ecommercebackend.utils.OrderListConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place-order")
    public String placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        Long userId = Objects.requireNonNull(CurrentUserUtil.getCurrentUser()).getId();
        return orderService.placeOrder(userId, placeOrderDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderListDto> getOrder(@PathVariable("id") Long orderId){
        OrderListDto order = orderService.getOrderById(orderId);
        return  ResponseEntity.ok(order);
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<OrderReviewDto> getOrderReview(@PathVariable("id") Long orderId){
        OrderReviewDto order = orderService.getOrderReview(orderId);
        return  ResponseEntity.ok(order);
    }
    @GetMapping("/pagination/{status}")
    public OrderListResponseDto getOrdersWithPaginationAndSorting(
            @PathVariable String status,
            @RequestParam(value = "pageNo", defaultValue = OrderListConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = OrderListConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = OrderListConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = OrderListConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return orderService.getOrdersWithPaginationAndSorting(status, pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping
    public ResponseEntity<List<OrderListDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("my-orders")
    public ResponseEntity<List<MyOrdersDto>> getMyOrders() {
        Long userId = CurrentUserUtil.getCurrentUser().getId();
        return ResponseEntity.ok(orderService.getMyOrders(userId));
    }
}