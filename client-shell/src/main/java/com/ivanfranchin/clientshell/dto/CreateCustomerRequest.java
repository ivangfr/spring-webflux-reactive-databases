package com.ivanfranchin.clientshell.dto;

public record CreateCustomerRequest(String name, String email, String city, String street, String number) {
}
