package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.order.BillingInfoDto;
import com.busayo.ecommercebackend.dto.order.ContactInfoDto;
import com.busayo.ecommercebackend.dto.order.ShippingAddressDto;
import com.busayo.ecommercebackend.dto.orderDetails.OrderDetails2Dto;
import com.busayo.ecommercebackend.model.OrderDetail;

public interface OrderDetailsService {
    void addOrderedProducts(OrderDetail orderDetail);

    OrderDetails2Dto getOrderItems(Long orderId);

    BillingInfoDto viewBillingInfo(Long orderId);

    ShippingAddressDto viewShippingAddress(Long orderId);

    ContactInfoDto viewCustomerContactInfo(Long orderId);
}
