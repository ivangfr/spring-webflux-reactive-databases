package com.ivanfranchin.customerapi.rest.dto;

public record CustomerResponse(Long id, String name, String email, String city, String street, String number) {
}
