package com.ivanfranchin.clientshell.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class CreateNotificationRequest {

    String orderId;
}
