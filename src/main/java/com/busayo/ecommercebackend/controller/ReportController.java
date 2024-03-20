package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.order.StatisticsResponse2Dto;
import com.busayo.ecommercebackend.dto.order.StatisticsResponseDto;
import com.busayo.ecommercebackend.service.ReportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/products/size")
    public StatisticsResponseDto numberOfProducts() {
        return reportService.numberOfAllProducts();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/size")
    public StatisticsResponseDto numberOfUsers() {
        return reportService.numberOfAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/revenue/sum")
    public StatisticsResponse2Dto totalRevenue(){
        return reportService.totalRevenue();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders/size")
    public StatisticsResponseDto totalNumberOfOrders(@RequestParam(name = "dateFilter", required = false) String dateFilter) {
        return reportService.totalNumberOfAllOrders(dateFilter);
    }
}