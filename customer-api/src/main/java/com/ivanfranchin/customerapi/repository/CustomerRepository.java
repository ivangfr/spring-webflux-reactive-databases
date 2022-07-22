package com.ivanfranchin.customerapi.repository;

import com.ivanfranchin.customerapi.model.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {
}
