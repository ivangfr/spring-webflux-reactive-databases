package com.ivanfranchin.clientshell.command;

import com.ivanfranchin.clientshell.client.NotificationApiClient;
import com.ivanfranchin.clientshell.dto.CreateNotificationRequest;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.Objects;

@Component
public class NotificationShellCommands {

    private final NotificationApiClient notificationApiClient;
    private final ObjectMapper objectMapper;

    public NotificationShellCommands(NotificationApiClient notificationApiClient, ObjectMapper objectMapper) {
        this.notificationApiClient = notificationApiClient;
        this.objectMapper = objectMapper;
    }

    @Command(name = "get-notification", description = "Get notification by id", group = "Notification Commands")
    public String getNotification(@Option(longName = "id", required = true) String id) {
        try {
            return notificationApiClient.getNotification(id).map(objectMapper::writeValueAsString).block();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Command(name = "get-notifications", description = "Get all notifications", group = "Notification Commands")
    public String getNotifications(@Option(longName = "orderId") String orderId) {
        try {
            return Objects.requireNonNull(notificationApiClient.getNotifications(orderId).map(objectMapper::writeValueAsString).collectList().block()).toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Command(name = "create-notification", description = "Create notification", group = "Notification Commands")
    public String createNotification(@Option(longName = "orderId", required = true) String orderId) {
        CreateNotificationRequest createNotificationRequest = new CreateNotificationRequest(orderId);
        try {
            return notificationApiClient.createNotification(createNotificationRequest).map(objectMapper::writeValueAsString).block();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
