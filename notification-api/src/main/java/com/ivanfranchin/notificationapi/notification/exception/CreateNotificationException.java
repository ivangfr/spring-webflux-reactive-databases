package com.ivanfranchin.notificationapi.notification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CreateNotificationException extends RuntimeException {

    public CreateNotificationException(String message) {
        super(message);
    }
}
