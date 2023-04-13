package com.busayo.ecommercebackend.controller;

import com.busayo.ecommercebackend.dto.authentication.JwtAuthResponseDto;
import com.busayo.ecommercebackend.dto.authentication.SignInDto;
import com.busayo.ecommercebackend.dto.authentication.SignUpDto;
import com.busayo.ecommercebackend.dto.user.ChangePasswordDto;
import com.busayo.ecommercebackend.service.AuthenticationService;
import com.busayo.ecommercebackend.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ProfileService profileService;



    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody SignInDto signInDto){
        String token = authenticationService.signIn(signInDto);
        JwtAuthResponseDto jwtAuthResponse = new JwtAuthResponseDto();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody SignUpDto signUpDto){
        String response = authenticationService.signUp(signUpDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/reset-password")
    @ResponseBody
    public String changePassword(@RequestBody ChangePasswordDto changePasswordDto){

        return profileService.changePassword(changePasswordDto);
    }
}
