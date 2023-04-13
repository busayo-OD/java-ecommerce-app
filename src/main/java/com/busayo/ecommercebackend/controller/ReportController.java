package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}