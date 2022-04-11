package com.mycompany.orderapi.rest.collector;

import com.mycompany.orderapi.aspect.LogInputAndExecutionTime;
import com.mycompany.orderapi.client.CustomerApiClient;
import com.mycompany.orderapi.client.ProductApiClient;
import com.mycompany.orderapi.client.dto.CustomerResponse;
import com.mycompany.orderapi.client.dto.ProductResponse;
import com.mycompany.orderapi.mapper.OrderMapper;
import com.mycompany.orderapi.model.Order;
import com.mycompany.orderapi.rest.dto.OrderDetailedResponse;
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
