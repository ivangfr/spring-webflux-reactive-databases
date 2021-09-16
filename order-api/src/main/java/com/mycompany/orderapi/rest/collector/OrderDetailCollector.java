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

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrderDetailCollector {

    private final CustomerApiClient customerApiClient;
    private final ProductApiClient productApiClient;
    private final OrderMapper orderMapper;

    @LogInputAndExecutionTime
    public OrderDetailedResponse getOrderDetailed(Order order) {
        CompletableFuture<OrderDetailedResponse.CustomerDto> customerCompletableFuture =
                CompletableFuture.supplyAsync(() -> customerApiClient.getCustomer(order.getCustomerId())
                        .onErrorReturn(new CustomerResponse())
                        .map(orderMapper::toOrderDetailedResponseCustomerDto)
                        .block());

        CompletableFuture<Set<OrderDetailedResponse.ProductDto>> productsCompletableFuture =
                CompletableFuture.supplyAsync(() -> order.getProducts().parallelStream().map(product -> {
                    OrderDetailedResponse.ProductDto orderDetailedResponseProductDto = orderMapper.toOrderDetailedResponseProductDto(product);
                    ProductResponse productResponse = productApiClient.getProduct(product.getId())
                            .onErrorReturn(new ProductResponse())
                            .block();
                    orderMapper.updateOrderDetailedResponseProductDtoFromProductResponse(productResponse, orderDetailedResponseProductDto);
                    return orderDetailedResponseProductDto;
                }).collect(Collectors.toSet()));

        OrderDetailedResponse orderDetailedResponse = orderMapper.toOrderDetailedResponse(order);
        CompletableFuture.allOf(customerCompletableFuture, productsCompletableFuture)
                .thenAccept(aVoid -> {
                    orderDetailedResponse.setCustomer(customerCompletableFuture.join());
                    orderDetailedResponse.setProducts(productsCompletableFuture.join());
                }).join();

        return orderDetailedResponse;
    }
}
