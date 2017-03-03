package de.codecentric.resilient.transport.api.gateway.rest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import de.codecentric.resilient.dto.*;
import de.codecentric.resilient.transport.api.gateway.commands.AddressCommand;
import de.codecentric.resilient.transport.api.gateway.commands.BookingCommand;
import de.codecentric.resilient.transport.api.gateway.commands.CustommerCommand;
import de.codecentric.resilient.transport.api.gateway.dto.BookingRequestDTO;
import de.codecentric.resilient.transport.api.gateway.dto.CustomerResponseDTO;
import de.codecentric.resilient.transport.api.gateway.mapper.BookingRequestMapper;

/**
 * @author Benjamin Wilms
 */
@RestController
@RequestMapping("rest/transport/booking")
public class TransportApiGatewayController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportApiGatewayController.class);

    private final RestTemplate restTemplate;

    private final BookingRequestMapper bookingRequestMapper;

    @Autowired
    public TransportApiGatewayController(RestTemplate restTemplate, BookingRequestMapper bookingRequestMapper) {
        this.restTemplate = restTemplate;
        this.bookingRequestMapper = bookingRequestMapper;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BookingServiceResponseDTO> create(@RequestBody BookingRequestDTO bookingRequestDTO,
            HttpServletRequest request) {

        LOGGER.debug(LOGGER.isDebugEnabled() ? "Booking Request: " + bookingRequestDTO.toString() : null);

        BookingServiceResponseDTO responseDTO = executeBookingRequest(bookingRequestDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    private BookingServiceResponseDTO executeBookingRequest(@RequestBody BookingRequestDTO bookingRequestDTO) {
        // 1.) check customer
        Future<CustomerResponseDTO> customerRequestFuture = checkCustomer(bookingRequestDTO);

        // 2.) check sender address
        Future<AddressResponseDTO> senderAddressFuture = checkAddress(exctractSenderAddress(bookingRequestDTO));

        // 3.) check receiver address
        Future<AddressResponseDTO> receiverAddressFuture = checkAddress(exctractReceiverAddress(bookingRequestDTO));

        CustomerDTO customerDTO = null;
        AddressDTO addressSenderDTO = null;
        AddressDTO addressReceiverDTO = null;
        try {
            customerDTO = bookingRequestMapper.mapCustomerResponseToCustomerDTO(customerRequestFuture.get());
            addressSenderDTO = bookingRequestMapper.mapAddressResponseToAddressDTO(senderAddressFuture.get());
            addressReceiverDTO = bookingRequestMapper.mapAddressResponseToAddressDTO(receiverAddressFuture.get());

        } catch (InterruptedException e) {
            String msg = "Interrupted Exception -  while receive future";
            LOGGER.error(msg, e);
            return createBookingResponseFallback(msg, e);
        } catch (ExecutionException e) {
            String msg = "Execution Exception -  while receive future";
            LOGGER.error(msg, e);
            return createBookingResponseFallback(msg, e);
        }

        // 4.) create booking with
        return createBookingRequest(customerDTO, addressSenderDTO, addressReceiverDTO);
    }

    private BookingServiceResponseDTO createBookingResponseFallback(String msg, Exception e) {

        BookingServiceResponseDTO bookingServiceResponseDTO = new BookingServiceResponseDTO();
        bookingServiceResponseDTO.setErrorMsg(msg + ": " + e.getMessage());
        return bookingServiceResponseDTO;
    }

    @RequestMapping(value = "generate")
    public ResponseEntity<BookingRequestDTO> generate(HttpServletRequest request) {

        return new ResponseEntity<>(new BookingRequestDTO(), HttpStatus.OK);
    }

    private BookingServiceResponseDTO createBookingRequest(CustomerDTO customerDTO, AddressDTO senderAddressDTO,
            AddressDTO receiverAddressDTO) {

        BookingServiceRequestDTO bookingServiceRequestDTO =
            bookingRequestMapper.mapBookingRequestToBookingServiceRequest(customerDTO, senderAddressDTO, receiverAddressDTO);

        BookingCommand bookingCommand = new BookingCommand(bookingServiceRequestDTO, restTemplate);
        return bookingCommand.execute();

    }

    private Future<AddressResponseDTO> checkAddress(AddressDTO addressDTO) {
        AddressCommand addressCommand = new AddressCommand(addressDTO, restTemplate);
        return addressCommand.queue();
    }

    private Future<CustomerResponseDTO> checkCustomer(BookingRequestDTO bookingRequestDTO) {

        CustommerCommand custommerCommand = new CustommerCommand(bookingRequestDTO.getCustomerId(), restTemplate);

        return custommerCommand.queue();
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
