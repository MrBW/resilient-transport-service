package com.codecentric.de.customer.repository;

import org.springframework.data.repository.CrudRepository;
import com.codecentric.de.customer.entity.Customer;

/**
 * @author Benjamin Wilms
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByName(String name);
}
