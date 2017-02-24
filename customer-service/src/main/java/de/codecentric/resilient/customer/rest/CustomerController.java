package de.codecentric.resilient.customer.rest;

import javax.servlet.http.HttpServletRequest;

import de.codecentric.resilient.customer.entity.Customer;
import de.codecentric.resilient.customer.service.CustomerService;
import de.codecentric.resilient.customer.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import de.codecentric.resilient.dto.CustomerDTO;

/**
 * @author Benjamin Wilms
 */

@RestController
@RequestMapping("rest/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
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
        customerDTO.setInstance(request.getLocalName() + " : " + request.getLocalPort());

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
        customerDTO.setInstance(request.getLocalName() + " : " + request.getLocalPort());

        return new ResponseEntity<>(customerDTO, HttpStatus.FOUND);

    }

    private CustomerDTO mapToCustomerDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getName());
    }
}
