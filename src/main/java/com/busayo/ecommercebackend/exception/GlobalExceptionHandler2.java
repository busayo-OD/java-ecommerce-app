package com.busayo.ecommercebackend.exception;

import com.busayo.ecommercebackend.dto.errorDetails.ErrorDetailsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler2 {
    public ResponseEntity<ErrorDetailsDto> handlerBlogApiException(EcommerceAPIException2 exception,
                                                                   WebRequest webRequest){

        ErrorDetailsDto errorDetails = new ErrorDetailsDto(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);


    }
}
