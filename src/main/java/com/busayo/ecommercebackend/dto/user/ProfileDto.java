package com.busayo.ecommercebackend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String avatar;

    private String address;

    private String phoneNumber;

    private String state;

    private String country;
}
