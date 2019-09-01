package com.mycompany.clientshell.converter;

import com.mycompany.clientshell.dto.CreateOrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CreateOrderProductConverter implements Converter<String, Set<CreateOrderDto.ProductDto>> {

    @Override
    public Set<CreateOrderDto.ProductDto> convert(String s) {
        return Stream.of(s.split(";"))
                .map(o -> o.split(":"))
                .map(o -> new CreateOrderDto.ProductDto(o[0], Integer.valueOf(o[1])))
                .collect(Collectors.toSet());
    }

}
