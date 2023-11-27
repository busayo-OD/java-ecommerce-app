package com.busayo.ecommercebackend.exception;

public class ProductDuplicateException extends EcommerceAPIException{
    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "Product";

    @Override
    public String getMessage() {
        return String.format("%s entry already exists in record.", ENTITY_NAME);
    }
}
