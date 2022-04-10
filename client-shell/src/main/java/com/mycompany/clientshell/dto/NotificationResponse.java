package com.mycompany.clientshell.dto;

import lombok.Getter;

@Getter
public class NotificationResponse {

    private String id;
    private String orderId;
    private String email;
    private String createdAt;
}
