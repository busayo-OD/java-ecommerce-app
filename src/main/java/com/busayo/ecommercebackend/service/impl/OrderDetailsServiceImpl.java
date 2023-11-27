package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.order.ContactInfoDto;
import com.busayo.ecommercebackend.dto.orderDetails.OrderDetails2Dto;
import com.busayo.ecommercebackend.dto.orderDetails.OrderDetailsDto;
import com.busayo.ecommercebackend.exception.OrderNotFoundException;
import com.busayo.ecommercebackend.model.Order;
import com.busayo.ecommercebackend.model.OrderDetail;
import com.busayo.ecommercebackend.model.ShippingAddress;
import com.busayo.ecommercebackend.repository.OrderDetailsRepository;
import com.busayo.ecommercebackend.repository.OrderRepository;
import com.busayo.ecommercebackend.service.OrderDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderRepository orderRepository;

    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository,
                                   OrderRepository orderRepository) {
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void addOrderedProducts(OrderDetail orderDetail) {
        orderDetailsRepository.save(orderDetail);
    }

    @Override
    public OrderDetails2Dto getOrderItems(Long orderId) {
        List<OrderDetail> orderItemsList = orderDetailsRepository.findByOrderId(orderId);
        List<OrderDetailsDto> orderItems = new ArrayList<>();
        for (OrderDetail orderItem:orderItemsList){
            OrderDetailsDto orderDetailsDto = getDtoFromOrderItems(orderItem);
            orderItems.add(orderDetailsDto);
        }
        double total = 0;
        for (OrderDetailsDto orderDetailsDto :orderItems){
            total += orderDetailsDto.getSubTotal();
        }
        return new OrderDetails2Dto(orderItems,total);
    }

    @Override
    public ShippingAddress viewShippingAddress(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        return order.getShippingAddress();
    }

    @Override
    public ContactInfoDto viewCustomerContactInfo(Long orderId) {
        ContactInfoDto contactInfo = new ContactInfoDto();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        contactInfo.setFirstName(order.getUser().getFirstName());
        contactInfo.setLastName(order.getUser().getLastName());
        contactInfo.setUsername(order.getUser().getUsername());
        contactInfo.setAvatar(order.getUser().getAvatar());
        contactInfo.setEmail(order.getUser().getEmail());
        contactInfo.setPhoneNumber(order.getUser().getPhoneNumber());
        contactInfo.setAddress(order.getUser().getAddress());
        contactInfo.setState(order.getUser().getState());
        contactInfo.setCountry(order.getUser().getCountry());
        return contactInfo;

    }

    public static OrderDetailsDto getDtoFromOrderItems(OrderDetail orderDetail) {
        return new OrderDetailsDto(orderDetail);
    }
}
