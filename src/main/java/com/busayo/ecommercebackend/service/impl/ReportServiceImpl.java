package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.order.StatisticsResponseDto;
import com.busayo.ecommercebackend.exception.InvalidDateFilterException;
import com.busayo.ecommercebackend.model.Order;
import com.busayo.ecommercebackend.model.Product;
import com.busayo.ecommercebackend.model.User;
import com.busayo.ecommercebackend.repository.OrderRepository;
import com.busayo.ecommercebackend.repository.ProductRepository;
import com.busayo.ecommercebackend.repository.UserRepository;
import com.busayo.ecommercebackend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    public int numberOfAllProducts(){

        List<Product> products = productRepository.findAll();
        System.out.print("Total number of products: " + products.size());

        return products.size();

    }

    @Override
    public int numberOfAllUsers() {
        List<User> users = userRepository.findAll();
        System.out.print("Total number of customers: " + users.size());

        return users.size();
    }

    @Override
    public double totalRevenue() {
        double sum = 0;
        List<Order> allOrders = orderRepository.findAll();
        for(Order order : allOrders){
            try{
                double amount = order.getAmount();
                sum += amount;
            } catch (NumberFormatException ex){
                sum += 0.0;
            }
        }
        System.out.println("Total sum: " + sum);
        return sum;
    }

    @Override
    public int numberOfAllOrders() {
        List<Order> orders = orderRepository.findAll();
        System.out.print("Total number of orders: " + orders.size());

        return orders.size();
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
