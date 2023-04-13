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

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "customer_name", length = 256, nullable = false)
    private String customerName;

    @Column(name = "customer_address", length = 256, nullable = false)
    private String customerAddress;

    private String state;

    @Column(name = "customer_email", length = 128, nullable = false)
    private String customerEmail;

    @Column(name = "customer_phone", length = 128, nullable = false)
    private String customerPhone;

    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Temporal(TemporalType.DATE)
    @Column(name = "order_date")
    private Date orderDate;

    private String paymentType;

    private String status;

    @OneToMany(mappedBy = "order")
    private Set<OrderDetail> orderDetails;
}

