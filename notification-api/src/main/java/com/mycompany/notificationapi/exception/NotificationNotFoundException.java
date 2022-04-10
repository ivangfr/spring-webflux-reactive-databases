package com.mycompany.notificationapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException(Long id) {
        super(String.format("Notification with id %s not found.", id));
    }
}
