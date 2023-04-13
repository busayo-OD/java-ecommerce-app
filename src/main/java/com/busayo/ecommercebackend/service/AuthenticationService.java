package com.busayo.ecommercebackend.service;

import com.busayo.ecommercebackend.dto.authentication.SignInDto;
import com.busayo.ecommercebackend.dto.authentication.SignUpDto;

public interface AuthenticationService {
    String signIn(SignInDto signInDto);
    String signUp(SignUpDto signUpDto);
}
