package com.busayo.ecommercebackend.exception;

public class PasswordMismatchException extends EcommerceAPIException{
    @Override
    public String getMessage() {
        return "New password and existing or confirmation password should match.";
    }
}
