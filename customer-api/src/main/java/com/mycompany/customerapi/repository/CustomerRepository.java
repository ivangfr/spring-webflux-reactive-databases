package com.mycompany.customerapi.repository;

import com.mycompany.customerapi.model.Customer;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;

public interface CustomerRepository extends ReactiveCouchbaseRepository<Customer, String> {
}
