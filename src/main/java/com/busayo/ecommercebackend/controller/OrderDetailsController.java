package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.order.BillingInfoDto;
import com.busayo.ecommercebackend.dto.order.ContactInfoDto;
import com.busayo.ecommercebackend.dto.order.ShippingAddressDto;
import com.busayo.ecommercebackend.dto.orderDetails.OrderDetails2Dto;
import com.busayo.ecommercebackend.model.ShippingAddress;
import com.busayo.ecommercebackend.service.OrderDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-items")
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    public OrderDetailsController(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetails2Dto> getOrderItems(@PathVariable("id") Long orderId){
        return ResponseEntity.ok(orderDetailsService.getOrderItems(orderId));
    }

//    @GetMapping("/billing-info/{id}")
//    public ResponseEntity<BillingInfoDto> getBillingInfo(@PathVariable("id") Long orderId){
//        return ResponseEntity.ok(orderDetailsService.viewBillingInfo(orderId));
//    }

    @GetMapping("/shipping-address/{id}")
    public ResponseEntity<ShippingAddress> getShippingAddress(@PathVariable("id") Long orderId){
        return ResponseEntity.ok(orderDetailsService.viewShippingAddress(orderId));
    }

    @GetMapping("/contact-info/{id}")
    public ResponseEntity<ContactInfoDto> getContactInfo(@PathVariable("id") Long orderId){
        return ResponseEntity.ok(orderDetailsService.viewCustomerContactInfo(orderId));
    }
}
