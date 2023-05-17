package com.busayo.ecommercebackend.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", nullable = false)
    private int orderNumber;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Temporal(TemporalType.DATE)
    @Column(name = "order_date")
    private Date orderDate;

    private String paymentType;

    private String status;

    @Column(name = "state")
    private String state;

    @Column(name = "shipping_address")
    private String shippingAddress;

}

