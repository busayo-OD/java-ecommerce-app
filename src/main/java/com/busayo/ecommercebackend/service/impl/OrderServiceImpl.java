package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.dto.cart.CartDto;
import com.busayo.ecommercebackend.dto.cart.CartItemDto;
import com.busayo.ecommercebackend.dto.order.*;
import com.busayo.ecommercebackend.exception.OrderNotFoundException;
import com.busayo.ecommercebackend.exception.ShippingMethodNotFoundException;
import com.busayo.ecommercebackend.exception.UserNotFoundException;
import com.busayo.ecommercebackend.model.*;
import com.busayo.ecommercebackend.repository.*;
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
    OrderRepository orderRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CartService cartService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    ShippingAddressRepository shippingAddressRepository;

    @Autowired
    ShippingMethodRepository shippingMethodRepository;

    @Autowired
    CouponRepository couponRepository;

    @Override
    public String placeOrder(Long userId, PlaceOrderDto placeOrderDto) {
        CartDto cartDto = cartService.getMyCart(userId);
        List<CartItemDto> cartItemDtoList = cartDto.getCartItems();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (cartItemDtoList.isEmpty()) {
            return "Cart is empty";
        } else {
            Order newOrder = new Order();

            // Set basic order properties
            newOrder.setOrderDate(new Date());
            newOrder.setUser(user);
            newOrder.setOrderStatus("In Progress");
            newOrder.setPaymentStatus("NO");
            newOrder.setStatus("Active");

            // Generate a random order number
            int randomNum = ThreadLocalRandom.current().nextInt(1000, 10000);
            newOrder.setOrderNumber(randomNum);
            ShippingAddress shippingAddress = createShippingAddress(placeOrderDto);

            Long shippingMethodId = placeOrderDto.getShippingMethodId();
            ShippingMethod shippingMethod = shippingMethodRepository.findById(shippingMethodId)
                    .orElseThrow(() -> new ShippingMethodNotFoundException(shippingMethodId));

            newOrder.setShippingAddress(shippingAddress);
            newOrder.setShippingMethod(shippingMethod);

            String couponCode = placeOrderDto.getCouponCode();
            Coupon coupon = couponRepository.findByCouponCode(couponCode);
            double discount = (coupon != null) ? coupon.getDiscountValue() : 0.0;
            newOrder.setDiscount(discount);
            newOrder.setAmount(cartDto.getTotal() - discount);

            orderRepository.save(newOrder);

            createAndSaveOrderItems(newOrder, cartItemDtoList);
            cartService.deleteUserCartItems(userId);
            createAndSaveNotification(user, newOrder);

            return "Successful";
        }
    }

    private ShippingAddress createShippingAddress(PlaceOrderDto placeOrderDto) {
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setFirstname(placeOrderDto.getFirstname());
        shippingAddress.setLastname(placeOrderDto.getLastname());
        shippingAddress.setEmail(placeOrderDto.getEmail());
        shippingAddress.setPhoneNumber(placeOrderDto.getPhoneNumber());
        shippingAddress.setStreetAddress(placeOrderDto.getStreetAddress());
        shippingAddress.setCity(placeOrderDto.getCity());
        shippingAddress.setState(placeOrderDto.getState());
        shippingAddress.setZipCode(placeOrderDto.getZipCode());
        shippingAddressRepository.save(shippingAddress);
        return shippingAddress;
    }

    private void createAndSaveOrderItems(Order newOrder, List<CartItemDto> cartItemDtoList) {
        for (CartItemDto cartItemDto : cartItemDtoList) {
            OrderDetail orderItem = new OrderDetail();
            orderItem.setSubTotal(cartItemDto.getProduct().getPrice() * cartItemDto.getQuantity());
            orderItem.setProduct(cartItemDto.getProduct());
            orderItem.setQuantity(cartItemDto.getQuantity());

            orderItem.setOrder(newOrder);
            orderItem.setStatus("Active");
            orderDetailsRepository.save(orderItem);
        }
    }

    private void createAndSaveNotification(User user, Order newOrder) {
        Notification notification = new Notification();
        notification.setStatus("Active");
        notification.setTitle(user.getFirstName() + " " + user.getLastName());
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

        List<OrderListDto> content = orderList.stream().map(this::mapToOrderListDto).collect(Collectors.toList());

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
        return orders.stream().map(this::mapToOrderListDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MyOrdersDto> getMyOrders(Long userId) {
        List<Order> myOrders = orderRepository.findByUserId(userId);
        return myOrders.stream().map(this::mapToMyOrdersDto)
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

    @Override
    public OrderListDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)

                .orElseThrow(() -> new OrderNotFoundException(orderId));
        return mapToOrderListDto(order);
    }

    @Override
    public OrderReviewDto getOrderReview(Long orderId) {
        Order order = orderRepository.findById(orderId)

                .orElseThrow(() -> new OrderNotFoundException(orderId));
        return mapToOrderReviewDto(order);
    }

    private OrderReviewDto mapToOrderReviewDto(Order order) {
        OrderReviewDto orderReviewDto = new OrderReviewDto();

        // Calculate the total number of items in the order
        int numberOfItems = 0;
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            numberOfItems += orderDetail.getQuantity();
        }

        double subtotal = order.getAmount();
        double discount = order.getDiscount();
        double shipping = order.getShippingMethod().getPrice();
        double vat = 10;
        double total = subtotal + shipping + vat - discount;

        orderReviewDto.setNoOfItems(numberOfItems);
        orderReviewDto.setSubtotal(subtotal);
        orderReviewDto.setDiscount(discount);
        orderReviewDto.setShipping(shipping);
        orderReviewDto.setVat(vat);
        orderReviewDto.setTotal(total);

        return orderReviewDto;
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
