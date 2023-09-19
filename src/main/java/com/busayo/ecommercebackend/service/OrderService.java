package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.order.*;

import java.util.List;

public interface OrderService {
    String placeOrder(Long userId, PlaceOrderDto placeOrderDto);
    OrderListResponseDto getOrdersWithPaginationAndSorting(String status, int pageNo, int pageSize, String sortBy, String sortDir);
    List<OrderListDto> getAllOrders();
    List<MyOrdersDto> getMyOrders(Long userId);
    void editBillingInfo(BillingInfoDto billingAddressDto, Long userId);
    OrderListDto getOrderById(Long orderId);
    OrderReviewDto getOrderReview(Long orderId);
}
