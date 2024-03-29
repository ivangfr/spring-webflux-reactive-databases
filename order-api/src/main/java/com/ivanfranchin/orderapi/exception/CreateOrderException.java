package com.ivanfranchin.orderapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CreateOrderException extends RuntimeException {

    public CreateOrderException(String message) {
        super(message);
    }
}
