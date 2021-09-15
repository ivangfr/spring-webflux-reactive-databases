package com.mycompany.orderapi.mapper;

import com.mycompany.orderapi.client.dto.CustomerDto;
import com.mycompany.orderapi.client.dto.ProductDto;
import com.mycompany.orderapi.model.Order;
import com.mycompany.orderapi.model.Product;
import com.mycompany.orderapi.rest.dto.CreateOrderDto;
import com.mycompany.orderapi.rest.dto.OrderDetailedDto;
import com.mycompany.orderapi.rest.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OrderMapper {

    Order toOrder(CreateOrderDto createOrderDto);

    @Mapping(source = "key.orderId", target = "orderId")
    @Mapping(source = "key.created", target = "created")
    OrderDto toOrderDto(Order order);

    @Mapping(source = "key.orderId", target = "orderId")
    @Mapping(source = "key.created", target = "created")
    OrderDetailedDto toOrderDetailedDto(Order order);

    OrderDetailedDto.CustomerDto toOrderDetailedDtoCustomerDto(CustomerDto customerDto);

    OrderDetailedDto.ProductDto toOrderDetailedDtoProductDto(Product product);

    void updateOrderDetailedDtoProductDtoFromProductDto(
            ProductDto productDto,@MappingTarget OrderDetailedDto.ProductDto orderDetailedDtoProductDto);
}
