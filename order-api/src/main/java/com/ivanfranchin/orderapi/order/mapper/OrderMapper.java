package com.ivanfranchin.orderapi.order.mapper;

import com.ivanfranchin.orderapi.client.CustomerApiClient;
import com.ivanfranchin.orderapi.client.ProductApiClient;
import com.ivanfranchin.orderapi.order.exception.CreateOrderException;
import com.ivanfranchin.orderapi.order.model.Order;
import com.ivanfranchin.orderapi.order.model.Product;
import com.ivanfranchin.orderapi.order.dto.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrderMapper {

    private final CustomerApiClient customerApiClient;
    private final ProductApiClient productApiClient;

    public Mono<Order> toOrder(CreateOrderRequest createOrderRequest) {
        return validateCustomerAndProducts(createOrderRequest)
                .flatMap(isValid -> {
                    Order order = new Order();
                    order.setCustomerId(createOrderRequest.customerId());
                    Set<Product> products = createOrderRequest.products()
                            .stream()
                            .map(productDto -> new Product(productDto.id(), productDto.quantity()))
                            .collect(Collectors.toSet());
                    order.setProducts(products);
                    return Mono.just(order);
                });
    }

    private Mono<Boolean> validateCustomerAndProducts(CreateOrderRequest createOrderRequest) {
        Mono<Boolean> customerResponseMono = customerApiClient.getCustomer(createOrderRequest.customerId())
                .onErrorMap(e -> new CreateOrderException(
                        String.format("Unable to get customer id %s", createOrderRequest.customerId())))
                .map(Objects::nonNull);

        Mono<Boolean> productsResponseMono = Flux.fromIterable(createOrderRequest.products())
                .flatMap(productDto -> productApiClient.getProduct(productDto.id())
                        .onErrorMap(e -> new CreateOrderException(
                                String.format("Unable to get product id %s", productDto.id())))
                        .map(Objects::nonNull))
                .reduce(true, (b1, b2) -> b1 && b2);

        return Mono.zip(customerResponseMono, productsResponseMono)
                .map(tuple2 -> tuple2.getT1() && tuple2.getT2());
    }
}
