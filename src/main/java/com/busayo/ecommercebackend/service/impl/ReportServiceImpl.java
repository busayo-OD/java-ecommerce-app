package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.order.StatisticsResponse2Dto;
import com.busayo.ecommercebackend.dto.order.StatisticsResponseDto;
import com.busayo.ecommercebackend.exception.InvalidDateFilterException;
import com.busayo.ecommercebackend.model.Order;
import com.busayo.ecommercebackend.model.Product;
import com.busayo.ecommercebackend.model.User;
import com.busayo.ecommercebackend.repository.OrderRepository;
import com.busayo.ecommercebackend.repository.ProductRepository;
import com.busayo.ecommercebackend.repository.UserRepository;
import com.busayo.ecommercebackend.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public ReportServiceImpl(ProductRepository productRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public StatisticsResponseDto numberOfAllProducts() {
        List<Product> products = productRepository.findAll();
        int totalProducts = products.size();

        StatisticsResponseDto responseDto = new StatisticsResponseDto();
        responseDto.setTotal(totalProducts);
        return responseDto;
    }

    @Override
    public StatisticsResponseDto numberOfAllUsers() {
        List<User> users = userRepository.findAll();
        int totalUsers = users.size();

        StatisticsResponseDto responseDto = new StatisticsResponseDto();
        responseDto.setTotal(totalUsers);
        return responseDto;
    }

    @Override
    public StatisticsResponse2Dto totalRevenue() {
        double sum = 0;
        List<Order> allOrders = orderRepository.findAll();
        for (Order order : allOrders) {
            try {
                double amount = order.getAmount();
                sum += amount;
            } catch (NumberFormatException ex) {
                sum += 0.0;
            }
        }

        StatisticsResponse2Dto responseDto = new StatisticsResponse2Dto();
        responseDto.setAmount(sum);
        return responseDto;
    }

    @Override
    public StatisticsResponseDto numberOfAllOrders() {
        List<Order> orders = orderRepository.findAll();
        long totalOrders = orders.size();
        StatisticsResponseDto responseDto = new StatisticsResponseDto();
        responseDto.setTotal(totalOrders);
        return responseDto;
    }

    @Override
    public StatisticsResponseDto totalNumberOfAllOrders(String dateFilter) {
        StatisticsResponseDto responseDTO = new StatisticsResponseDto();

        Date startDate;
        Date endDate = new Date();

        if (dateFilter == null ) {
            startDate = null;
        } else {
            switch (dateFilter.toLowerCase()) {
                case "daily":
                    startDate = getStartOfDay(new Date());
                    break;
                case "weekly":
                    startDate = getStartOfWeek(new Date());
                    break;
                case "monthly":
                    startDate = getStartOfMonth(new Date());
                    break;
                default:
                    throw new InvalidDateFilterException();
            }
        }

        long totalCount;

        if (startDate != null) {
            totalCount = orderRepository.countByOrderDateBetween(startDate, endDate);
        } else {
            totalCount = orderRepository.count();
        }

        responseDTO.setTotal(totalCount);
        return responseDTO;
    }

    private Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date getStartOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return getStartOfDay(calendar.getTime());
    }

    private Date getStartOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getStartOfDay(calendar.getTime());
    }

}
