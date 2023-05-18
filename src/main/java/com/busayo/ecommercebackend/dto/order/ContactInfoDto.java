package com.busayo.ecommercebackend.dto.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContactInfoDto {

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String avatar;

    private String address;

    private String state;

    private String country;

    private String phoneNumber;
}

