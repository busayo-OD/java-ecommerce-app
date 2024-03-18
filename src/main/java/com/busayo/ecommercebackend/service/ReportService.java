package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.order.StatisticsResponseDto;

public interface ReportService {
    int numberOfAllProducts();
    int numberOfAllUsers();
    double totalRevenue();
    int numberOfAllOrders();
    StatisticsResponseDto totalNumberOfAllOrders(String dateFilter);
}
