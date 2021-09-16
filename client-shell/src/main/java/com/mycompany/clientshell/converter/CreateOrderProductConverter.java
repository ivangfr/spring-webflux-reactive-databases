package com.mycompany.clientshell.converter;

import com.mycompany.clientshell.dto.CreateOrderRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CreateOrderProductConverter implements Converter<String, Set<CreateOrderRequest.ProductDto>> {

    @Override
    public Set<CreateOrderRequest.ProductDto> convert(String s) {
        return Stream.of(s.split(";"))
                .map(o -> o.split(":"))
                .map(o -> new CreateOrderRequest.ProductDto(o[0], Integer.valueOf(o[1])))
                .collect(Collectors.toSet());
    }
}
