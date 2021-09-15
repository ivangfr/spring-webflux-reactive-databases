package com.mycompany.productapi.mapper;

import com.mycompany.productapi.model.Product;
import com.mycompany.productapi.rest.dto.CreateProductDto;
import com.mycompany.productapi.rest.dto.ProductDto;
import com.mycompany.productapi.rest.dto.UpdateProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductMapper {

    Product toProduct(CreateProductDto createProductDto);

    ProductDto toProductDto(Product product);

    void updateProductFromDto(UpdateProductDto updateProductDto, @MappingTarget Product product);
}
