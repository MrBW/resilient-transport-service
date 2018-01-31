package de.codecentric.resilient.customer.rest;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import de.codecentric.resilient.customer.entity.Customer;
import de.codecentric.resilient.customer.exception.CustomerNotFoundException;
import de.codecentric.resilient.customer.service.CustomerService;
import de.codecentric.resilient.dto.CustomerDTO;

/**
 * @author Benjamin Wilms
 */

@RestController
@RequestMapping("rest/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final Tracer tracer;

    @Autowired
    public CustomerController(CustomerService customerService, Tracer tracer) {
        this.customerService = customerService;
        this.tracer = tracer;
    }

    @RequestMapping(value = "search/id")
    @ResponseBody
    public ResponseEntity<CustomerDTO> searchById(@RequestParam("customerId") Long customerId, HttpServletRequest request) {

        Customer customer;
        try {
            customer = customerService.findCustomerById(customerId);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomerDTO customerDTO = mapToCustomerDTO(customer);

        appendSpan(customerDTO.getCustomerName(), "customer-name");
        appendSpan(String.valueOf(customerDTO.getCustomerId()), "customer-id");

        return new ResponseEntity<>(customerDTO, HttpStatus.FOUND);

    }

    @RequestMapping(value = "search/name")
    @ResponseBody
    public ResponseEntity<CustomerDTO> searchByName(@RequestParam("name") String name, HttpServletRequest request) {

        Customer customer;
        try {
            customer = customerService.findByName(name);

        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomerDTO customerDTO = mapToCustomerDTO(customer);

        appendSpan(customerDTO.getCustomerName(), "customer-name");
        appendSpan(String.valueOf(customerDTO.getCustomerId()), "customer-id");

        return new ResponseEntity<>(customerDTO, HttpStatus.FOUND);

    }

    private CustomerDTO mapToCustomerDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getName());
    }

    private void appendSpan(String value, String key) {
        Span span = tracer.getCurrentSpan();
        String baggageKey = key;
        String baggageValue = value;
        span.setBaggageItem(baggageKey, baggageValue);
        tracer.addTag(baggageKey, baggageValue);
    }
}
