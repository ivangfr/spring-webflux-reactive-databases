package com.ivanfranchin.orderapi.client.dto;

import java.math.BigDecimal;

public record ProductResponse(String id, String name, BigDecimal price) {

    public static ProductResponse EMPTY = new ProductResponse("", "", BigDecimal.ZERO);
}
