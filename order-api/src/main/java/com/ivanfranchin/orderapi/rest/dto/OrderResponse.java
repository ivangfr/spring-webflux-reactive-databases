package com.ivanfranchin.orderapi.rest.dto;

import com.ivanfranchin.orderapi.model.Order;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record OrderResponse(UUID orderId, String status, LocalDateTime created, Set<ProductDto> products,
                            String customerId) {

    @Schema(name = "OrderProductDto")
    public record ProductDto(String id, Integer quantity) {
    }

    public static OrderResponse from(Order order) {
        Set<OrderResponse.ProductDto> products = order.getProducts()
                .stream()
                .map(product -> new ProductDto(product.getId(), product.getQuantity()))
                .collect(Collectors.toSet());

        return new OrderResponse(
                order.getKey().getOrderId(),
                order.getStatus().name(),
                order.getKey().getCreated(),
                products,
                order.getCustomerId());
    }
}
