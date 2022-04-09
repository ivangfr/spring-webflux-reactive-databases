package com.mycompany.orderapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CreateOrderRequestInvalidException extends RuntimeException {

    public CreateOrderRequestInvalidException() {
        super("Create order request is invalid. The customer id and/or some product id(s) are not valid");
    }
}
