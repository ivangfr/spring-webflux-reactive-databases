package com.ivanfranchin.clientshell.dto;

import java.math.BigDecimal;

public record CreateProductRequest(String name, BigDecimal price) {
}
