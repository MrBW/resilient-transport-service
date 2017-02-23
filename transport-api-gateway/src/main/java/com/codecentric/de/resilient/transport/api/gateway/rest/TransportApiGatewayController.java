package com.codecentric.de.resilient.transport.api.gateway.rest;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.codecentric.de.resilient.dto.AddressDTO;
import com.codecentric.de.resilient.dto.BookingServiceRequestDTO;
import com.codecentric.de.resilient.dto.BookingServiceResponseDTO;
import com.codecentric.de.resilient.dto.CustomerDTO;
import com.codecentric.de.resilient.transport.api.gateway.dto.BookingRequestDTO;

/**
 * @author Benjamin Wilms
 */
@RestController
@RequestMapping("rest/transport/booking")
public class TransportApiGatewayController {

    private final RestTemplate restTemplate;

    @Autowired
    public TransportApiGatewayController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BookingServiceResponseDTO> create(@RequestBody BookingRequestDTO bookingRequestDTO,
            HttpServletRequest request) {

        // 1.) check customer
        CustomerDTO customerDTO = checkCustomer(bookingRequestDTO);

        // 2.) check sender address
        AddressDTO senderAddressDTO = checkAddress(exctractSenderAddress(bookingRequestDTO));

        // 3.) check receiver address
        AddressDTO receiverAddressDTO = checkAddress(exctractReceiverAddress(bookingRequestDTO));

        // 4.) create booking with
        BookingServiceResponseDTO responseDTO =
            createBookingRequest(customerDTO, senderAddressDTO, receiverAddressDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "generate")
    public ResponseEntity<BookingRequestDTO> generate(HttpServletRequest request) {

        return new ResponseEntity<>(new BookingRequestDTO(), HttpStatus.OK);
    }

    private BookingServiceResponseDTO createBookingRequest(CustomerDTO customerDTO, AddressDTO senderAddressDTO,
            AddressDTO receiverAddressDTO) {

        BookingServiceRequestDTO bookingServiceRequestDTO = new BookingServiceRequestDTO();
        bookingServiceRequestDTO.setSenderAddress(senderAddressDTO);
        bookingServiceRequestDTO.setReceiverAddress(receiverAddressDTO);
        bookingServiceRequestDTO.setCustomerDTO(customerDTO);

        return restTemplate.postForObject("http://booking-service/rest/booking/create", bookingServiceRequestDTO,
            BookingServiceResponseDTO.class);

    }

    private AddressDTO checkAddress(AddressDTO addressDTO) {
        // Calling remote rest Service via Service Discorvery Eureka
        return restTemplate.postForObject("http://address-service/rest/address/", addressDTO, AddressDTO.class);
    }

    private CustomerDTO checkCustomer(BookingRequestDTO bookingRequestDTO) {

        // Calling remote rest Service via Service Discorvery Eureka
        CustomerDTO customerDTO =
            restTemplate.getForObject("http://customer-service/rest/customer/search/id?customerId={customerId}",
                CustomerDTO.class, bookingRequestDTO.getCustomerId());

        return customerDTO;
    }

    private AddressDTO exctractSenderAddress(BookingRequestDTO bookingRequestDTO) {
        return new AddressDTO(bookingRequestDTO.getSenderCountry(), bookingRequestDTO.getSenderCity(),
            bookingRequestDTO.getSenderPostcode(), bookingRequestDTO.getSenderStreet(),
            bookingRequestDTO.getSenderStreetNumber());
    }

    private AddressDTO exctractReceiverAddress(BookingRequestDTO bookingRequestDTO) {
        return new AddressDTO(bookingRequestDTO.getReceiverCountry(), bookingRequestDTO.getReceiverCity(),
            bookingRequestDTO.getReceiverPostcode(), bookingRequestDTO.getReceiverStreet(),
            bookingRequestDTO.getReceiverStreetNumber());
    }

}
