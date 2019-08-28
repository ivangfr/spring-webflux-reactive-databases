package com.mycompany.orderapi.rest;

import com.mycompany.orderapi.client.CustomerApiClient;
import com.mycompany.orderapi.client.ProductApiClient;
import com.mycompany.orderapi.client.dto.CustomerDto;
import com.mycompany.orderapi.client.dto.ProductDto;
import com.mycompany.orderapi.model.Order;
import com.mycompany.orderapi.model.OrderKey;
import com.mycompany.orderapi.rest.dto.CreateOrderDto;
import com.mycompany.orderapi.rest.dto.OrderDetailedDto;
import com.mycompany.orderapi.rest.dto.OrderDto;
import com.mycompany.orderapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerApiClient customerApiClient;
    private final ProductApiClient productApiClient;
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
            Instant start = Instant.now();

            CompletableFuture<OrderDetailedDto.CustomerDto> customerCompletableFuture =
                    CompletableFuture.supplyAsync(() -> {
                        CustomerDto customerDto = customerApiClient.getCustomer(order.getCustomerId()).block();
                        return mapperFacade.map(customerDto, OrderDetailedDto.CustomerDto.class);
                    });

            CompletableFuture<Set<OrderDetailedDto.ProductDto>> productsCompletableFuture =
                    CompletableFuture.supplyAsync(() -> order.getProducts().parallelStream().map(product -> {
                        OrderDetailedDto.ProductDto productDto = mapperFacade.map(product, OrderDetailedDto.ProductDto.class);
                        ProductDto productApiDto = productApiClient.getProduct(product.getId()).block();
                        mapperFacade.map(productApiDto, productDto);
                        return productDto;
                    }).collect(Collectors.toSet()));

            OrderDetailedDto orderDetailedDto = mapperFacade.map(order, OrderDetailedDto.class);
            CompletableFuture.allOf(customerCompletableFuture, productsCompletableFuture).thenAccept(aVoid -> {
                orderDetailedDto.setCustomer(customerCompletableFuture.join());
                orderDetailedDto.setProducts(productsCompletableFuture.join());
            }).join();

            log.info("Execution time: {}", Duration.between(start, Instant.now()).getSeconds());

            return orderDetailedDto;
        });
    }

}
