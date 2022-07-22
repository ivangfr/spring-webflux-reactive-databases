package com.ivanfranchin.productapi.mapper;

import com.ivanfranchin.productapi.rest.dto.CreateProductRequest;
import com.ivanfranchin.productapi.rest.dto.ProductResponse;
import com.ivanfranchin.productapi.model.Product;
import com.ivanfranchin.productapi.rest.dto.UpdateProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    Product toProduct(CreateProductRequest createProductRequest);

    ProductResponse toProductResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    void updateProductFromRequest(UpdateProductRequest updateProductRequest, @MappingTarget Product product);
}
