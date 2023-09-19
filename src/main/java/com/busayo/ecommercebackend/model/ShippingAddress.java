package com.busayo.ecommercebackend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "shipping_address")
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    private String email;
    @Column(name = "street_address")
    private String streetAddress;
    private String state;
    private String city;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "zip_code")
    private String zipCode;
}
