package de.codecentric.resilient.customer.repository;

import de.codecentric.resilient.customer.entity.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Benjamin Wilms
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByName(String name);
}
