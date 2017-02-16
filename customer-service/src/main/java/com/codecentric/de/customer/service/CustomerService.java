package com.codecentric.de.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.codecentric.de.customer.entity.Customer;
import com.codecentric.de.customer.exception.CustomerNotFoundException;
import com.codecentric.de.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

/**
 * @author Benjamin Wilms
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }

    public Customer findCustomerById(Long customerId) throws CustomerNotFoundException {

        Customer customer = null;
        try {
            customer = customerRepository.findOne(customerId);

        } catch (Exception e) {
            throw new CustomerNotFoundException("Error by finding customer by id: " + customerId, e);
        }

        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found by id: " + customerId);
        }

        return customer;

    }

    public Customer findByName(String name) {
        Customer customer;
        try {
            customer = customerRepository.findByName(name);

        } catch (Exception e) {
            throw new CustomerNotFoundException("Error by finding customer by name: " + name, e);
        }

        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found by name: " + name);
        }

        return customer;
    }
}
