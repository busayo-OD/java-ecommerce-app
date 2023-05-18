package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.cart.CartDto;
import com.busayo.ecommercebackend.dto.cart.CartItemDto;
import com.busayo.ecommercebackend.dto.order.*;
import com.busayo.ecommercebackend.exception.UserNotFoundException;
import com.busayo.ecommercebackend.model.*;
import com.busayo.ecommercebackend.repository.NotificationRepository;
import com.busayo.ecommercebackend.repository.OrderDetailsRepository;
import com.busayo.ecommercebackend.repository.OrderRepository;
import com.busayo.ecommercebackend.repository.UserRepository;
import com.busayo.ecommercebackend.service.CartService;
import com.busayo.ecommercebackend.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private  OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void placeOrder(Long userId, PlaceOrderDto placeOrderDto) {

        CartDto cartDto = cartService.getMyCart(userId);

        List<CartItemDto> cartItemDtoList = cartDto.getCartItems();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // create the order and save it
        Order newOrder = new Order();
        newOrder.setOrderDate(new Date());
        newOrder.setUser(user);
        newOrder.setAmount(cartDto.getTotal());
        newOrder.setOrderStatus("In Progress");
        newOrder.setPaymentStatus("NO");
        newOrder.setStatus("Active");
        newOrder.setShippingAddress(placeOrderDto.getShippingAddress());
        newOrder.setState(placeOrderDto.getState());

        int min = 1000;
        int max = 5000;
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        newOrder.setOrderNumber(randomNum);
        orderRepository.save(newOrder);

        for (CartItemDto cartItemDto : cartItemDtoList) {
            // create orderItem and save each one
            OrderDetail orderItem = new OrderDetail();
//            orderItem.setCreatedDate(new Date());
            orderItem.setSubTotal(cartItemDto.getProduct().getPrice() * cartItemDto.getQuantity());
            orderItem.setProduct(cartItemDto.getProduct());
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setOrder(newOrder);
            orderItem.setStatus("Active");
            // add to order item list
            orderDetailsRepository.save(orderItem);
        }
        cartService.deleteUserCartItems(userId);

        Notification notification = new Notification();

        notification.setImage(user.getAvatar());
        notification.setStatus("Active");
        notification.setTitle("");
        notification.setText("New order by " + user.getUsername() + " with order number " + newOrder.getOrderNumber());

        notificationRepository.save(notification);
    }

    @Override
    public OrderListResponseDto getOrdersWithPaginationAndSorting(String status, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Order> orders = orderRepository.findByStatus(status, pageable);

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

    @Override
    public List<MyOrdersDto> getMyOrders(Long userId) {
        List<Order> myOrders = orderRepository.findByUserId(userId);
        return myOrders.stream().map((order) -> mapToMyOrdersDto(order))
                .collect(Collectors.toList());
    }

    @Override
    public void editBillingInfo(BillingInfoDto billingInfoDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.setFirstName(billingInfoDto.getFirstName());
        user.setLastName(billingInfoDto.getLastName());
        user.setPhoneNumber(billingInfoDto.getPhoneNumber());
        user.setAddress(billingInfoDto.getAddress());
        user.setState(billingInfoDto.getState());

        userRepository.save(user);
    }

    private OrderListDto mapToOrderListDto(Order order){

        OrderListDto orderListDto = new OrderListDto();
        orderListDto.setId(order.getId());
        orderListDto.setOrderNumber(order.getOrderNumber());
        orderListDto.setOrderStatus(order.getOrderStatus());
        orderListDto.setPaymentStatus(order.getPaymentStatus());
        orderListDto.setCustomerName(order.getUser().getFirstName() + " " + order.getUser().getLastName());
        orderListDto.setAmount(order.getAmount());
        orderListDto.setOrderDate(order.getOrderDate());
        return orderListDto;
    }

    private MyOrdersDto mapToMyOrdersDto(Order order){

        MyOrdersDto myOrdersDto = new MyOrdersDto();
        myOrdersDto.setId(order.getId());
        myOrdersDto.setOrderNumber(order.getOrderNumber());
        myOrdersDto.setOrderStatus(order.getOrderStatus());
        myOrdersDto.setPaymentStatus(order.getPaymentStatus());
        myOrdersDto.setAmount(order.getAmount());
        myOrdersDto.setOrderDate(order.getOrderDate());
        return myOrdersDto;
    }
}
