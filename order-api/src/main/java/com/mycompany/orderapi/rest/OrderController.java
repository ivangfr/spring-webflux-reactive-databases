package com.mycompany.orderapi.rest;

import com.mycompany.orderapi.exception.OrderNotFoundException;
import com.mycompany.orderapi.model.Order;
import com.mycompany.orderapi.model.OrderItem;
import com.mycompany.orderapi.rest.dto.AddOrderItemDto;
import com.mycompany.orderapi.rest.dto.CreateOrderDto;
import com.mycompany.orderapi.rest.dto.OrderDto;
import com.mycompany.orderapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final MapperFacade mapperFacade;

    @Transactional
    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable Long orderId) throws OrderNotFoundException {
        Order order = orderService.validateAndGetOrder(orderId);
        return mapperFacade.map(order, OrderDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderDto createOrder(@Valid @RequestBody CreateOrderDto createOrderDto) {
        Order order = mapperFacade.map(createOrderDto, Order.class);
        order.setCreated(LocalDateTime.now());
        order = orderService.saveOrder(order);
        return mapperFacade.map(order, OrderDto.class);
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{orderId}/items")
    public OrderDto addOrderItem(@PathVariable Long orderId, @Valid @RequestBody AddOrderItemDto addOrderItemDto)
            throws OrderNotFoundException {
        Order order = orderService.validateAndGetOrder(orderId);

        OrderItem orderItem = mapperFacade.map(addOrderItemDto, OrderItem.class);
        orderItem.setOrder(order);
        order.getItems().add(orderItem);

        order = orderService.saveOrder(order);
        return mapperFacade.map(order, OrderDto.class);
    }

}
