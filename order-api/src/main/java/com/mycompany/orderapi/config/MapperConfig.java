package com.mycompany.orderapi.config;

import com.mycompany.orderapi.model.Order;
import com.mycompany.orderapi.rest.dto.OrderDetailedDto;
import com.mycompany.orderapi.rest.dto.OrderDto;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    MapperFactory mapperFactory() {
        DefaultMapperFactory defaultMapperFactory = new DefaultMapperFactory.Builder().useAutoMapping(true).mapNulls(false).build();

        defaultMapperFactory.classMap(Order.class, OrderDto.class)
                .byDefault()
                .customize(new CustomMapper<Order, OrderDto>() {
                    @Override
                    public void mapAtoB(Order order, OrderDto orderDto, MappingContext context) {
                        super.mapAtoB(order, orderDto, context);
                        orderDto.setOrderId(order.getKey().getOrderId());
                        orderDto.setCreated(order.getKey().getCreated());
                    }
                }).register();

        defaultMapperFactory.classMap(Order.class, OrderDetailedDto.class)
                .byDefault()
                .customize(new CustomMapper<Order, OrderDetailedDto>() {
                    @Override
                    public void mapAtoB(Order order, OrderDetailedDto orderDetailedDto, MappingContext context) {
                        super.mapAtoB(order, orderDetailedDto, context);
                        orderDetailedDto.setOrderId(order.getKey().getOrderId());
                        orderDetailedDto.setCreated(order.getKey().getCreated());
                    }
                }).register();

        return defaultMapperFactory;
    }

    @Bean
    MapperFacade mapperFacade() {
        return mapperFactory().getMapperFacade();
    }

}
