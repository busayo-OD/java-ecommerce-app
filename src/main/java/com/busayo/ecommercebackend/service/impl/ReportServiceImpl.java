package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.model.Order;
import com.busayo.ecommercebackend.model.Product;
import com.busayo.ecommercebackend.model.User;
import com.busayo.ecommercebackend.repository.OrderRepository;
import com.busayo.ecommercebackend.repository.ProductRepository;
import com.busayo.ecommercebackend.repository.UserRepository;
import com.busayo.ecommercebackend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
