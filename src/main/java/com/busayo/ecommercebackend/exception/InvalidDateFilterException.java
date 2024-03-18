package com.busayo.ecommercebackend.exception;

public class InvalidDateFilterException extends EcommerceAPIException{
    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "Date Filter";

    @Override
    public String getMessage() {
        return "Date filter supplied is invalid.";
    }
}
