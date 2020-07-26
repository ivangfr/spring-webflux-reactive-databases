package com.mycompany.orderapi.rest.collector;

import com.mycompany.orderapi.aspect.LogInputAndExecutionTime;
import com.mycompany.orderapi.client.CustomerApiClient;
import com.mycompany.orderapi.client.ProductApiClient;
import com.mycompany.orderapi.client.dto.CustomerDto;
import com.mycompany.orderapi.client.dto.ProductDto;
import com.mycompany.orderapi.mapper.OrderMapper;
import com.mycompany.orderapi.model.Order;
import com.mycompany.orderapi.rest.dto.OrderDetailedDto;
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
    public OrderDetailedDto getOrderDetailed(Order order) {
        CompletableFuture<OrderDetailedDto.CustomerDto> customerCompletableFuture =
                CompletableFuture.supplyAsync(() -> customerApiClient.getCustomer(order.getCustomerId())
                        .onErrorReturn(new CustomerDto())
                        .map(orderMapper::toOrderDetailedDtoCustomerDto)
                        .block());

        CompletableFuture<Set<OrderDetailedDto.ProductDto>> productsCompletableFuture =
                CompletableFuture.supplyAsync(() -> order.getProducts().parallelStream().map(product -> {
                    OrderDetailedDto.ProductDto orderDetailedDtoProductDto = orderMapper.toOrderDetailedDtoProductDto(product);
                    ProductDto productDto = productApiClient.getProduct(product.getId())
                            .onErrorReturn(new ProductDto())
                            .block();
                    orderMapper.updateOrderDetailedDtoProductDtoFromProductDto(productDto, orderDetailedDtoProductDto);
                    return orderDetailedDtoProductDto;
                }).collect(Collectors.toSet()));

        OrderDetailedDto orderDetailedDto = orderMapper.toOrderDetailedDto(order);
        CompletableFuture.allOf(customerCompletableFuture, productsCompletableFuture).thenAccept(aVoid -> {
            orderDetailedDto.setCustomer(customerCompletableFuture.join());
            orderDetailedDto.setProducts(productsCompletableFuture.join());
        }).join();

        return orderDetailedDto;
    }

}
