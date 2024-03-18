package com.busayo.ecommercebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CategoryDuplicateException.class)
    public Object duplicate(CategoryDuplicateException ex) {
        final Map<String, Object> errors = new HashMap<>();
        errors.put("entityName", CategoryDuplicateException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.CONFLICT.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CartItemNotFoundException.class)
    public Object duplicate(CartItemNotFoundException ex) {
        final Map<String, Object> errors = new HashMap<>();
        errors.put("entityName", CartItemNotFoundException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.CONFLICT.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ProductTypeDuplicateException.class)
    public Object duplicate(ProductTypeDuplicateException ex) {
        final Map<String, Object> errors = new HashMap<>();
        errors.put("entityName", ProductTypeDuplicateException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.CONFLICT.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public Object exists(EmailAlreadyExistsException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", EmailAlreadyExistsException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.CONFLICT.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public Object exists(UsernameAlreadyExistsException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", UsernameAlreadyExistsException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.CONFLICT.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ShippingMethodNotFoundException.class)
    public Object notFound(ShippingMethodNotFoundException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", ShippingMethodNotFoundException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.NOT_FOUND.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ShippingMethodDuplicateException.class)
    public Object duplicate(ShippingMethodDuplicateException ex) {
        final Map<String, Object> errors = new HashMap<>();
        errors.put("entityName", ShippingMethodDuplicateException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.CONFLICT.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotificationNotFoundException.class)
    public Object notFound(NotificationNotFoundException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", NotificationNotFoundException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.NOT_FOUND.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public Object notFound(OrderNotFoundException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", OrderNotFoundException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.NOT_FOUND.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ReviewNotFoundException.class)
    public Object notFound(ReviewNotFoundException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", ReviewNotFoundException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.NOT_FOUND.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ReviewResponseNotFoundException.class)
    public Object notFound(ReviewResponseNotFoundException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", ReviewResponseNotFoundException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.NOT_FOUND.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public Object notFound(CategoryNotFoundException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", CategoryNotFoundException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.NOT_FOUND.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CouponNotFoundException.class)
    public Object notFound(CouponNotFoundException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", CouponNotFoundException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.NOT_FOUND.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmailNotFoundException.class)
    public Object notFound(EmailNotFoundException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", EmailNotFoundException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.NOT_FOUND.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductTypeNotFoundException.class)
    public Object notFound(ProductTypeNotFoundException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", ProductTypeNotFoundException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.NOT_FOUND.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BrandNotFoundException.class)
    public Object notFound(BrandNotFoundException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", BrandNotFoundException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.NOT_FOUND.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public Object notFound(ProductNotFoundException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", ProductNotFoundException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.NOT_FOUND.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Object notFound(UserNotFoundException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", UserNotFoundException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.NOT_FOUND.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordMismatchException.class)
    public Object mismatch(PasswordMismatchException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", "User");
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.BAD_REQUEST.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(WishlistBasketAlreadyExistsException.class)
    public Object exists(WishlistBasketAlreadyExistsException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", WishlistBasketAlreadyExistsException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.CONFLICT.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(WishlistBasketNotFoundException.class)
    public Object notFound(WishlistBasketNotFoundException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", WishlistBasketNotFoundException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.NOT_FOUND.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDateFilterException.class)
    public Object invalidity(InvalidDateFilterException ex) {
        final Map<String, Object> errors = new HashMap<String, Object>();
        errors.put("entityName", InvalidDateFilterException.ENTITY_NAME);
        errors.put("message", ex.getMessage());
        ex.setCode(HttpStatus.BAD_REQUEST.value());
        errors.put("code", ex.getCode().toString());
        return errors;
    }

}

