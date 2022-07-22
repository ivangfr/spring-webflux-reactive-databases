package com.ivanfranchin.clientshell.command;

import com.google.gson.Gson;
import com.ivanfranchin.clientshell.client.NotificationApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class NotificationShellCommands {

    private final NotificationApiClient notificationApiClient;
    private final Gson gson;

    @ShellMethod("Get notification by id")
    public String getNotification(String id) {
        return notificationApiClient.getNotification(id).map(gson::toJson).block();
    }

    @ShellMethod("Get all notifications")
    public List<String> getNotifications(@ShellOption(defaultValue = "") String orderId) {
        return notificationApiClient.getNotifications(orderId).map(gson::toJson).collectList().block();
    }

    @ShellMethod("Create notification")
    public String createNotification(String orderId) {
        return notificationApiClient.createNotification(orderId).map(gson::toJson).block();
    }
}
