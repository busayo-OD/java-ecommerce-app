package com.busayo.ecommercebackend.dto.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BillingInfoDto {
    private String firstName;
    private String lastName;
    private String address;
    private String state;
    private String phoneNumber;
}
