package com.mycompany.productapi.mapper;

import com.mycompany.productapi.model.Product;
import com.mycompany.productapi.rest.dto.CreateProductRequest;
import com.mycompany.productapi.rest.dto.ProductResponse;
import com.mycompany.productapi.rest.dto.UpdateProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductMapper {

    Product toProduct(CreateProductRequest createProductRequest);

    ProductResponse toProductResponse(Product product);

    void updateProductFromRequest(UpdateProductRequest updateProductRequest, @MappingTarget Product product);
}
