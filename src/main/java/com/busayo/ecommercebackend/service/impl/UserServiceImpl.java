package com.busayo.ecommercebackend.service.impl;

import com.busayo.ecommercebackend.repository.UserRepository;
import com.busayo.ecommercebackend.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
