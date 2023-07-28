package com.busayo.ecommercebackend.exception;

public class WishlistBasketAlreadyExistsException extends EcommerceAPIException{
    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "WishlistBasket";

    @Override
    public String getMessage() {
        return String.format("%s already exists in the record.", ENTITY_NAME);
    }
}
