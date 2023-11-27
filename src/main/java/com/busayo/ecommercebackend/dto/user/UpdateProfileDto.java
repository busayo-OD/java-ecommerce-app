package com.busayo.ecommercebackend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileDto {

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String avatar;

    private String address;

    private String city;

    private String state;

    private String country;

    private String phoneNumber;

}
