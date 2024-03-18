package com.busayo.ecommercebackend.controller;

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

    @GetMapping("/numberofproducts")
    @ResponseBody
    public int numberOfProducts() {
        return reportService.numberOfAllProducts();
    }

    @GetMapping("/numberofusers")
    @ResponseBody
    public int numberOfUsers() {
        return reportService.numberOfAllUsers();
    }

    @GetMapping("/totalrevenue")
    @ResponseBody
    public double totalRevenue(){
        return reportService.totalRevenue();
    }

    @GetMapping("/numberoforders")
    @ResponseBody
    public int numberOfOrders() {
        return reportService.numberOfAllOrders();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orders/size")
    public StatisticsResponseDto totalNumberOfOrders(@RequestParam(name = "dateFilter", required = false) String dateFilter) {
        return reportService.totalNumberOfAllOrders(dateFilter);
    }
}