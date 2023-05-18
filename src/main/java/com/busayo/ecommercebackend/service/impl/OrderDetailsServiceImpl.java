package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.order.BillingInfoDto;
import com.busayo.ecommercebackend.dto.order.ContactInfoDto;
import com.busayo.ecommercebackend.dto.order.ShippingAddressDto;
import com.busayo.ecommercebackend.dto.orderDetails.OrderDetails2Dto;
import com.busayo.ecommercebackend.dto.orderDetails.OrderDetailsDto;
import com.busayo.ecommercebackend.exception.OrderNotFoundException;
import com.busayo.ecommercebackend.model.Order;
import com.busayo.ecommercebackend.model.OrderDetail;
import com.busayo.ecommercebackend.repository.OrderDetailsRepository;
import com.busayo.ecommercebackend.repository.OrderRepository;
import com.busayo.ecommercebackend.service.OrderDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private OrderDetailsRepository orderDetailsRepository;
    private OrderRepository orderRepository;

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
    public BillingInfoDto viewBillingInfo(Long orderId) {
        BillingInfoDto billingInfo = new BillingInfoDto();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        billingInfo.setFirstName(order.getUser().getFirstName());
        billingInfo.setLastName(order.getUser().getLastName());
        billingInfo.setPhoneNumber(order.getUser().getPhoneNumber());
        billingInfo.setAddress(order.getUser().getAddress());
        billingInfo.setState(order.getUser().getState());

        return billingInfo;
    }

    @Override
    public ShippingAddressDto viewShippingAddress(Long orderId) {

        ShippingAddressDto shippingAddress = new ShippingAddressDto();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        shippingAddress.setShippingAddress(order.getShippingAddress());
        shippingAddress.setState(order.getState());
        return shippingAddress;
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
