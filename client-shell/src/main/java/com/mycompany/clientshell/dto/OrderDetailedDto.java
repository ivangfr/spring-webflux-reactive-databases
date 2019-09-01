package com.mycompany.clientshell.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
public class OrderDetailedDto {

    private UUID orderId;
    private String status;
    private String created;
    private Set<ProductDto> products;
    private CustomerDto customer;

    @Data
    public static class ProductDto {

        private String id;
        private String name;
        private Integer quantity;
        private BigDecimal price;

    }

    @Data
    public static class CustomerDto {

        private String id;
        private String name;
        private String email;
        private AddressDto address;

        @Data
        public static class AddressDto {

            private String city;
            private String street;
            private String number;

        }

    }

}
