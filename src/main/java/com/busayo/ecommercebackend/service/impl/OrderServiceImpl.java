package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.order.OrderListDto;
import com.busayo.ecommercebackend.dto.order.OrderListResponseDto;
import com.busayo.ecommercebackend.dto.order.OrderRequestDto;
import com.busayo.ecommercebackend.dto.order.OrderResponseDto;
import com.busayo.ecommercebackend.model.*;
import com.busayo.ecommercebackend.repository.OrderDetailsRepository;
import com.busayo.ecommercebackend.repository.OrderRepository;
import com.busayo.ecommercebackend.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderDetailsRepository orderDetailsRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

//    Disclaimer!!!!!

//    The APIs below were created to test out the Order listing feature in the admin dashboard.


    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequest) {
        Order order = orderRequest.getOrder();
        order.setOrderStatus("In Progress");
        order.setPaymentStatus("No");
        int min = 1000;
        int max = 5000;
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        order.setOrderNumber(randomNum);
        orderRepository.save(order);

        OrderResponseDto orderResponse = new OrderResponseDto();
        orderResponse.setOrderNumber(order.getOrderNumber());
        orderResponse.setStatus(order.getOrderStatus());
        orderResponse.setMessage("Success");
        return orderResponse;
    }

    @Override
    public OrderListResponseDto getAllOrdersWithPaginationAndSorting(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Order> orders = orderRepository.findAll(pageable);

        List<Order> orderList = orders.getContent();

        List<OrderListDto> content = orderList.stream().map(order -> mapToOrderListDto(order)).collect(Collectors.toList());

        OrderListResponseDto orderListResponseDto = new OrderListResponseDto();
        orderListResponseDto.setContent(content);
        orderListResponseDto.setPageNo(orders.getNumber());
        orderListResponseDto.setPageSize(orders.getSize());
        orderListResponseDto.setTotalElements(orders.getTotalElements());
        orderListResponseDto.setTotalPages(orders.getTotalPages());
        orderListResponseDto.setLast(orders.isLast());

        return orderListResponseDto;
    }

    @Override
    public List<OrderListDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map((order) -> mapToOrderListDto(order))
                .collect(Collectors.toList());
    }

    private OrderListDto mapToOrderListDto(Order order){

        OrderListDto orderListDto = new OrderListDto();
        orderListDto.setId(order.getId());
        orderListDto.setOrderNumber(order.getOrderNumber());
        orderListDto.setOrderStatus(order.getOrderStatus());
        orderListDto.setPaymentStatus(order.getPaymentStatus());
        orderListDto.setAmount(order.getAmount());
        orderListDto.setCustomerName(order.getCustomerName());
        orderListDto.setAmount(order.getAmount());
//        int orderDetail = orderDetailsRepository.findById()
//        orderListDto.setQuantity();
        orderListDto.setOrderDate(order.getOrderDate());
        return orderListDto;
    }
}
