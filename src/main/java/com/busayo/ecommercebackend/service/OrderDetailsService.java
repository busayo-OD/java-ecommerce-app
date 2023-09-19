package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.order.ContactInfoDto;
import com.busayo.ecommercebackend.dto.orderDetails.OrderDetails2Dto;
import com.busayo.ecommercebackend.model.OrderDetail;
import com.busayo.ecommercebackend.model.ShippingAddress;

public interface OrderDetailsService {
    void addOrderedProducts(OrderDetail orderDetail);

    OrderDetails2Dto getOrderItems(Long orderId);

//    BillingInfoDto viewBillingInfo(Long orderId);

    ShippingAddress viewShippingAddress(Long orderId);

    ContactInfoDto viewCustomerContactInfo(Long orderId);
}
