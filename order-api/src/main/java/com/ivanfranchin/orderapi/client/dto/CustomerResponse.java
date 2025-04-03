package com.ivanfranchin.orderapi.client.dto;

public record CustomerResponse(String id, String name, String email, String city, String street, String number) {

    public static CustomerResponse EMPTY = new CustomerResponse("", "", "", "", "", "");
}
