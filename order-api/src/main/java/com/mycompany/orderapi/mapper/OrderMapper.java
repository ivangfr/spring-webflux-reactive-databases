package com.mycompany.orderapi.mapper;

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

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OrderMapper {

    Order toOrder(CreateOrderRequest createOrderRequest);

    @Mapping(source = "key.orderId", target = "orderId")
    @Mapping(source = "key.created", target = "created")
    OrderResponse toOrderResponse(Order order);

    @Mapping(source = "key.orderId", target = "orderId")
    @Mapping(source = "key.created", target = "created")
    OrderDetailedResponse toOrderDetailedResponse(Order order);

    OrderDetailedResponse.CustomerDto toOrderDetailedResponseCustomerDto(CustomerResponse customerResponse);

    OrderDetailedResponse.ProductDto toOrderDetailedResponseProductDto(Product product);

    void updateOrderDetailedResponseProductDtoFromProductResponse(
            ProductResponse productResponse, @MappingTarget OrderDetailedResponse.ProductDto orderDetailedResponseProductDto);
}
