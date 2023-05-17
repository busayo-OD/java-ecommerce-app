package com.busayo.ecommercebackend.exception;

public class NotificationNotFoundException extends EcommerceAPIException{
    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "Notification";
    private Object entityId = null;

    public NotificationNotFoundException(Object entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getMessage() {
        return String.format("%s with an id %s cannot be found or does not exist in record.", ENTITY_NAME, entityId.toString());
    }
}
