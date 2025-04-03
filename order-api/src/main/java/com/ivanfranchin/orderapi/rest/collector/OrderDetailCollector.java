package com.ivanfranchin.orderapi.rest.collector;

import com.ivanfranchin.orderapi.aspect.LogInputAndExecutionTime;
import com.ivanfranchin.orderapi.client.CustomerApiClient;
import com.ivanfranchin.orderapi.client.ProductApiClient;
import com.ivanfranchin.orderapi.client.dto.CustomerResponse;
import com.ivanfranchin.orderapi.client.dto.ProductResponse;
import com.ivanfranchin.orderapi.model.Order;
import com.ivanfranchin.orderapi.rest.dto.OrderDetailedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class OrderDetailCollector {

    private final CustomerApiClient customerApiClient;
    private final ProductApiClient productApiClient;

    @LogInputAndExecutionTime
    public Mono<OrderDetailedResponse> getOrderDetailed(Order order) {
        Mono<OrderDetailedResponse.CustomerDto> customerDtoMono = customerApiClient.getCustomer(order.getCustomerId())
                .onErrorReturn(CustomerResponse.EMPTY)
                .map(OrderDetailCollector::from);

        Mono<Set<OrderDetailedResponse.ProductDto>> setProductDtoMono = Flux.fromIterable(order.getProducts())
                .flatMap(product -> productApiClient.getProduct(product.getId())
                        .onErrorReturn(ProductResponse.EMPTY)
                        .map(productResponse -> OrderDetailCollector.from(productResponse, product.getQuantity())))
                .collect(HashSet::new, Set::add);

        return Mono.zip(customerDtoMono, setProductDtoMono)
                .map(tuple2 -> from(order, tuple2.getT1(), tuple2.getT2()));
    }

    public static OrderDetailedResponse.CustomerDto from(CustomerResponse customerResponse) {
        return new OrderDetailedResponse.CustomerDto(
                customerResponse.id(),
                customerResponse.name(),
                customerResponse.email(),
                customerResponse.city(),
                customerResponse.street(),
                customerResponse.number());
    }

    public static OrderDetailedResponse.ProductDto from(ProductResponse productResponse, Integer quantity) {
        return new OrderDetailedResponse.ProductDto(
                productResponse.id(),
                productResponse.name(),
                quantity,
                productResponse.price());
    }

    public OrderDetailedResponse from(Order order,
                                      OrderDetailedResponse.CustomerDto customerDto,
                                      Set<OrderDetailedResponse.ProductDto> productDtos) {
        return new OrderDetailedResponse(
                order.getKey().getOrderId(),
                order.getStatus().name(),
                order.getKey().getCreated(),
                productDtos,
                customerDto);
    }
}
