package com.mycompany.orderapi.rest;

import com.mycompany.orderapi.model.Order;
import com.mycompany.orderapi.model.OrderKey;
import com.mycompany.orderapi.rest.dto.CreateOrderDto;
import com.mycompany.orderapi.rest.dto.OrderDetailedDto;
import com.mycompany.orderapi.rest.dto.OrderDto;
import com.mycompany.orderapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
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
import java.util.HashSet;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final MapperFacade mapperFacade;

    @GetMapping
    public Flux<OrderDto> getOrders() {
        return orderService.getOrders().map(order -> mapperFacade.map(order, OrderDto.class));
    }

    @GetMapping("/{orderId}")
    public Mono<OrderDto> getOrder(@PathVariable UUID orderId) {
        return orderService.validateAndGetOrder(orderId).map(order -> mapperFacade.map(order, OrderDto.class));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<OrderDto> createOrder(@Valid @RequestBody CreateOrderDto createOrderDto) {
        Order order = mapperFacade.map(createOrderDto, Order.class);
        order.setKey(new OrderKey(UUID.randomUUID(), LocalDateTime.now()));
        return orderService.saveOrder(order).map(o -> mapperFacade.map(o, OrderDto.class));
    }

    @GetMapping("/{orderId}/detailed")
    public Mono<OrderDetailedDto> getOrderDetailed(@PathVariable UUID orderId) {
        return orderService.validateAndGetOrder(orderId).map(order -> {
            OrderDetailedDto orderDetailedDto = mapperFacade.map(order, OrderDetailedDto.class);

            // get customer details from customer-api
            // get product details from product-api

            orderDetailedDto.setItems(new HashSet<>());
            orderDetailedDto.setCustomer(new OrderDetailedDto.CustomerDto());

            return orderDetailedDto;
        });
    }

}
