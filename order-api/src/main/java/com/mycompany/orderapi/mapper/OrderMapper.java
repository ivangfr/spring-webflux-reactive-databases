package com.mycompany.orderapi.mapper;

import com.mycompany.orderapi.client.CustomerApiClient;
import com.mycompany.orderapi.client.ProductApiClient;
import com.mycompany.orderapi.client.dto.CustomerResponse;
import com.mycompany.orderapi.client.dto.ProductResponse;
import com.mycompany.orderapi.exception.CreateOrderException;
import com.mycompany.orderapi.model.Order;
import com.mycompany.orderapi.model.Product;
import com.mycompany.orderapi.rest.dto.CreateOrderRequest;
import com.mycompany.orderapi.rest.dto.OrderDetailedResponse;
import com.mycompany.orderapi.rest.dto.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class OrderMapper {

    @Autowired
    private CustomerApiClient customerApiClient;

    @Autowired
    private ProductApiClient productApiClient;

    @Mapping(source = "key.orderId", target = "orderId")
    @Mapping(source = "key.created", target = "created")
    public abstract OrderResponse toOrderResponse(Order order);

    @Mapping(target = "customer", ignore = true)
    @Mapping(source = "key.orderId", target = "orderId")
    @Mapping(source = "key.created", target = "created")
    public abstract OrderDetailedResponse toOrderDetailedResponse(Order order);

    public abstract OrderDetailedResponse.CustomerDto toOrderDetailedResponseCustomerDto(CustomerResponse customerResponse);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "price", ignore = true)
    public abstract OrderDetailedResponse.ProductDto toOrderDetailedResponseProductDto(Product product);

    @Mapping(target = "quantity", ignore = true)
    public abstract void updateOrderDetailedResponseProductDtoFromProductResponse(
            ProductResponse productResponse, @MappingTarget OrderDetailedResponse.ProductDto orderDetailedResponseProductDto);

    public Mono<Order> toOrder(CreateOrderRequest createOrderRequest) {
        return validateCustomerAndProducts(createOrderRequest)
                .flatMap(isValid -> {
                    Order order = new Order();
                    order.setCustomerId(createOrderRequest.getCustomerId());
                    Set<Product> products = createOrderRequest.getProducts()
                            .stream()
                            .map(productDto -> new Product(productDto.getId(), productDto.getQuantity()))
                            .collect(Collectors.toSet());
                    order.setProducts(products);
                    return Mono.just(order);
                });
    }

    private Mono<Boolean> validateCustomerAndProducts(CreateOrderRequest createOrderRequest) {
        Mono<Boolean> customerResponseMono = customerApiClient.getCustomer(createOrderRequest.getCustomerId())
                .onErrorMap(e -> new CreateOrderException(
                        String.format("Unable to get customer id %s", createOrderRequest.getCustomerId())))
                .map(Objects::nonNull);

        Mono<Boolean> productsResponseMono = Flux.fromIterable(createOrderRequest.getProducts())
                .flatMap(productDto -> productApiClient.getProduct(productDto.getId())
                        .onErrorMap(e -> new CreateOrderException(
                                String.format("Unable to get product id %s", productDto.getId())))
                        .map(Objects::nonNull))
                .reduce(true, (b1, b2) -> b1 && b2);

        return Mono.zip(customerResponseMono, productsResponseMono)
                .map(tuple2 -> tuple2.getT1() && tuple2.getT2());
    }
}
