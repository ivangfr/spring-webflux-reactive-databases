package com.mycompany.notificationapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SendNotificationException extends RuntimeException {

    public SendNotificationException() {
        super("Invalid order id or customer doesn't have email.");
    }
}
