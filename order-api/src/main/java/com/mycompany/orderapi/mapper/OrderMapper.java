package com.mycompany.orderapi.mapper;

import com.mycompany.orderapi.client.CustomerApiClient;
import com.mycompany.orderapi.client.ProductApiClient;
import com.mycompany.orderapi.client.dto.CustomerResponse;
import com.mycompany.orderapi.client.dto.ProductResponse;
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
import reactor.core.publisher.Mono;

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

    @Mapping(source = "key.orderId", target = "orderId")
    @Mapping(source = "key.created", target = "created")
    public abstract OrderDetailedResponse toOrderDetailedResponse(Order order);

    public abstract OrderDetailedResponse.CustomerDto toOrderDetailedResponseCustomerDto(CustomerResponse customerResponse);

    public abstract OrderDetailedResponse.ProductDto toOrderDetailedResponseProductDto(Product product);

    public abstract void updateOrderDetailedResponseProductDtoFromProductResponse(
            ProductResponse productResponse, @MappingTarget OrderDetailedResponse.ProductDto orderDetailedResponseProductDto);

    public Mono<Order> toOrder(CreateOrderRequest createOrderRequest) {
        return validateCustomerAndProducts(createOrderRequest)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.empty();
                    }
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
                .onErrorReturn(new CustomerResponse())
                .map(customerResponse -> customerResponse.getId() != null);

        Mono<Boolean> productsResponseMono = createOrderRequest.getProducts()
                .parallelStream()
                .map(productDto -> productApiClient.getProduct(productDto.getId())
                        .onErrorReturn(new ProductResponse())
                        .flatMap(productResponse -> Mono.just(productResponse.getId() != null)))
                .reduce(Mono.just(true), (b1Mono, b2Mono) -> Mono.zip(b1Mono, b2Mono).map(tuple2 -> tuple2.getT1() && tuple2.getT2()));

        return Mono.zip(customerResponseMono, productsResponseMono)
                .map(tuple2 -> tuple2.getT1() && tuple2.getT2());
    }
}
