package com.busayo.ecommercebackend.exception;

public class CartItemNotFoundException extends EcommerceAPIException{
    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "Cart";
    private Object entityId = null;

    public CartItemNotFoundException(Object entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getMessage() {
        return String.format("%s with an id %s cannot be found or does not exist in record.", ENTITY_NAME, entityId.toString());
    }
}
