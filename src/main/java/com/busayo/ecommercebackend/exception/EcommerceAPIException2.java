package com.busayo.ecommercebackend.exception;

import org.springframework.http.HttpStatus;

public class EcommerceAPIException2 extends RuntimeException{
    private HttpStatus status;
    private String message;

    public EcommerceAPIException2(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public EcommerceAPIException2(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
