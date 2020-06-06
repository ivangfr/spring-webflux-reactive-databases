package com.mycompany.orderapi.rest;

import com.mycompany.orderapi.client.CustomerApiClient;
import com.mycompany.orderapi.client.ProductApiClient;
import com.mycompany.orderapi.client.dto.CustomerDto;
import com.mycompany.orderapi.client.dto.ProductDto;
import com.mycompany.orderapi.mapper.OrderMapper;
import com.mycompany.orderapi.model.Order;
import com.mycompany.orderapi.model.OrderKey;
import com.mycompany.orderapi.rest.dto.CreateOrderDto;
import com.mycompany.orderapi.rest.dto.OrderDetailedDto;
import com.mycompany.orderapi.rest.dto.OrderDto;
import com.mycompany.orderapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerApiClient customerApiClient;
    private final ProductApiClient productApiClient;
    private final OrderMapper orderMapper;

    @GetMapping
    public Flux<OrderDto> getOrders() {
        log.info("==> getOrders");
        return orderService.getOrders().map(orderMapper::toOrderDto);
    }

    @GetMapping("/{orderId}")
    public Mono<OrderDto> getOrder(@PathVariable UUID orderId) {
        log.info("==> getOrder {}", orderId);
        return orderService.validateAndGetOrder(orderId).map(orderMapper::toOrderDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<OrderDto> createOrder(@Valid @RequestBody CreateOrderDto createOrderDto) {
        log.info("==> createOrder {}", createOrderDto);
        Order order = orderMapper.toOrder(createOrderDto);
        order.setKey(new OrderKey(UUID.randomUUID(), LocalDateTime.now()));
        return orderService.saveOrder(order).map(orderMapper::toOrderDto);
    }

    @GetMapping("/{orderId}/detailed")
    public Mono<OrderDetailedDto> getOrderDetailed(@PathVariable UUID orderId) {
        return orderService.validateAndGetOrder(orderId).map(this::getOrderDetailed);
    }

    private OrderDetailedDto getOrderDetailed(Order order) {
        long start = System.currentTimeMillis();

        CompletableFuture<OrderDetailedDto.CustomerDto> customerCompletableFuture =
                CompletableFuture.supplyAsync(() -> {
                    CustomerDto customerDto = customerApiClient.getCustomer(order.getCustomerId()).block();
                    return orderMapper.toOrderDetailedDtoCustomerDto(customerDto);
                });

        CompletableFuture<Set<OrderDetailedDto.ProductDto>> productsCompletableFuture =
                CompletableFuture.supplyAsync(() -> order.getProducts().parallelStream().map(product -> {
                    OrderDetailedDto.ProductDto orderDetailedDtoProductDto = orderMapper.toOrderDetailedDtoProductDto(product);
                    ProductDto productDto = productApiClient.getProduct(product.getId()).block();
                    orderMapper.updateOrderDetailedDtoProductDtoFromProductDto(productDto, orderDetailedDtoProductDto);
                    return orderDetailedDtoProductDto;
                }).collect(Collectors.toSet()));

        OrderDetailedDto orderDetailedDto = orderMapper.toOrderDetailedDto(order);
        CompletableFuture.allOf(customerCompletableFuture, productsCompletableFuture).thenAccept(aVoid -> {
            orderDetailedDto.setCustomer(customerCompletableFuture.join());
            orderDetailedDto.setProducts(productsCompletableFuture.join());
        }).join();

        log.info("==> getOrderDetailed {}. Execution time: {} ms", order.getKey().getOrderId(), System.currentTimeMillis() - start);
        return orderDetailedDto;
    }

}
