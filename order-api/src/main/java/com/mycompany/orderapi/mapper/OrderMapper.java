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

import java.util.Set;
import java.util.concurrent.CompletableFuture;
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

    public Order toOrder(CreateOrderRequest createOrderRequest) {
        Order order = new Order();
        if (validateCustomerAndProducts(createOrderRequest)) {
            order.setCustomerId(createOrderRequest.getCustomerId());
            Set<Product> products = createOrderRequest.getProducts()
                    .stream()
                    .map(productDto -> new Product(productDto.getId(), productDto.getQuantity()))
                    .collect(Collectors.toSet());
            order.setProducts(products);
        }
        return order;
    }

    private boolean validateCustomerAndProducts(CreateOrderRequest createOrderRequest) {
        CompletableFuture<Boolean> customerCompletableFuture =
                CompletableFuture.supplyAsync(() -> customerApiClient.getCustomer(createOrderRequest.getCustomerId())
                        .onErrorReturn(new CustomerResponse())
                        .map(customerResponse -> customerResponse.getId() != null)
                        .block());

        CompletableFuture<Boolean> productsCompletableFuture =
                CompletableFuture.supplyAsync(() -> createOrderRequest.getProducts()
                        .parallelStream()
                        .allMatch(productDto -> productApiClient.getProduct(productDto.getId())
                                .onErrorReturn(new ProductResponse())
                                .map(productResponse -> productResponse.getId() != null)
                                .block()));

        return CompletableFuture.allOf(customerCompletableFuture, productsCompletableFuture)
                .thenApply(Boolean -> customerCompletableFuture.join() && productsCompletableFuture.join())
                .join();
    }
}
