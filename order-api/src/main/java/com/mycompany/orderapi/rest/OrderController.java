package com.mycompany.orderapi.rest;

import com.mycompany.orderapi.mapper.OrderMapper;
import com.mycompany.orderapi.model.OrderKey;
import com.mycompany.orderapi.rest.collector.OrderDetailCollector;
import com.mycompany.orderapi.rest.dto.CreateOrderRequest;
import com.mycompany.orderapi.rest.dto.OrderDetailedResponse;
import com.mycompany.orderapi.rest.dto.OrderResponse;
import com.mycompany.orderapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final OrderDetailCollector orderDetailCollector;

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<OrderResponse> getOrders() {
        return orderService.getOrders().map(orderMapper::toOrderResponse);
    }

    @GetMapping("/{orderId}")
    public Mono<OrderResponse> getOrder(@PathVariable UUID orderId) {
        return orderService.validateAndGetOrder(orderId).map(orderMapper::toOrderResponse);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        return orderMapper.toOrder(createOrderRequest).flatMap(order -> {
            order.setKey(new OrderKey(UUID.randomUUID(), LocalDateTime.now()));
            return orderService.saveOrder(order).map(orderMapper::toOrderResponse);
        });
    }

    @GetMapping("/{orderId}/detailed")
    public Mono<OrderDetailedResponse> getOrderDetailed(@PathVariable UUID orderId) {
        return orderService.validateAndGetOrder(orderId).flatMap(orderDetailCollector::getOrderDetailed);
    }
}
