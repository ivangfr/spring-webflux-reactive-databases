package com.mycompany.clientshell.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class CreateNotificationRequest {

    String orderId;
}
