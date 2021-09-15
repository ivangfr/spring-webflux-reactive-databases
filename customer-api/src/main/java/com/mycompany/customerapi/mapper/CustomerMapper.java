package com.mycompany.customerapi.mapper;

import com.mycompany.customerapi.model.Customer;
import com.mycompany.customerapi.rest.dto.CreateCustomerDto;
import com.mycompany.customerapi.rest.dto.CustomerDto;
import com.mycompany.customerapi.rest.dto.UpdateCustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    Customer toCustomer(CreateCustomerDto createCustomerDto);

    CustomerDto toCustomerDto(Customer customer);

    void updateCustomerFromDto(UpdateCustomerDto updateCustomerDto, @MappingTarget Customer customer);
}
