package com.ivanfranchin.orderapi.rest.collector;

import com.ivanfranchin.orderapi.aspect.LogInputAndExecutionTime;
import com.ivanfranchin.orderapi.model.Order;
import com.ivanfranchin.orderapi.rest.dto.OrderDetailedResponse;
import com.ivanfranchin.orderapi.client.CustomerApiClient;
import com.ivanfranchin.orderapi.client.ProductApiClient;
import com.ivanfranchin.orderapi.client.dto.CustomerResponse;
import com.ivanfranchin.orderapi.client.dto.ProductResponse;
import com.ivanfranchin.orderapi.mapper.OrderMapper;
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
    private final OrderMapper orderMapper;

    @LogInputAndExecutionTime
    public Mono<OrderDetailedResponse> getOrderDetailed(Order order) {
        Mono<OrderDetailedResponse.CustomerDto> customerDtoMono = customerApiClient.getCustomer(order.getCustomerId())
                .onErrorReturn(new CustomerResponse())
                .map(orderMapper::toOrderDetailedResponseCustomerDto);

        Mono<Set<OrderDetailedResponse.ProductDto>> setProductDtoMono = Flux.fromIterable(order.getProducts())
                .map(product -> productApiClient.getProduct(product.getId())
                        .onErrorReturn(new ProductResponse())
                        .map(productResponse -> {
                            OrderDetailedResponse.ProductDto orderDetailedResponseProductDto = orderMapper.toOrderDetailedResponseProductDto(product);
                            orderMapper.updateOrderDetailedResponseProductDtoFromProductResponse(productResponse, orderDetailedResponseProductDto);
                            return orderDetailedResponseProductDto;
                        })
                        .block())
                .collect(HashSet::new, Set::add);

        return Mono.zip(customerDtoMono, setProductDtoMono)
                .map(tuple2 -> {
                    OrderDetailedResponse orderDetailedResponse = orderMapper.toOrderDetailedResponse(order);
                    orderDetailedResponse.setCustomer(tuple2.getT1());
                    orderDetailedResponse.setProducts(tuple2.getT2());
                    return orderDetailedResponse;
                });
    }
}
