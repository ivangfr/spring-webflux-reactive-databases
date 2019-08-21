package com.mycompany.customerapi.repository;

import com.mycompany.customerapi.model.Customer;
import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;

@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "customer")
public interface CustomerRepository extends ReactiveCouchbaseRepository<Customer, String> {
}
