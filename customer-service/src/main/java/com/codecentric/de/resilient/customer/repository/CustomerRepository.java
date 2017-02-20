package com.codecentric.de.resilient.customer.repository;

import com.codecentric.de.resilient.customer.entity.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Benjamin Wilms
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByName(String name);
}
