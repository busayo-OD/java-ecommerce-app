package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.order.StatisticsResponse2Dto;
import com.busayo.ecommercebackend.dto.order.StatisticsResponseDto;

public interface ReportService {
    StatisticsResponseDto numberOfAllProducts();
    StatisticsResponseDto numberOfAllUsers();
    StatisticsResponse2Dto totalRevenue();
    StatisticsResponseDto numberOfAllOrders();
    StatisticsResponseDto totalNumberOfAllOrders(String dateFilter);
}
