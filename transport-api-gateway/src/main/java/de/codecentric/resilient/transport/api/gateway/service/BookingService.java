package de.codecentric.resilient.transport.api.gateway.service;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicPropertyFactory;
import de.codecentric.resilient.dto.*;
import de.codecentric.resilient.transport.api.gateway.commands.AddressCommand;
import de.codecentric.resilient.transport.api.gateway.commands.BookingCommand;
import de.codecentric.resilient.transport.api.gateway.commands.CustommerCommand;
import de.codecentric.resilient.transport.api.gateway.dto.BookingRequestDTO;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Service
public class BookingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);

    private final RestTemplate restTemplate;

    private DynamicBooleanProperty secondServiceCallEnabled =
        DynamicPropertyFactory.getInstance().getBooleanProperty("second.service.call", false);

    @Autowired
    public BookingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private Future<AddressResponseDTO> checkAddress(AddressDTO addressDTO) {

        AddressCommand addressCommand = new AddressCommand(addressDTO, restTemplate, secondServiceCallEnabled.get());
        return addressCommand.queue();
    }

    private Future<CustomerResponseDTO> checkCustomer(BookingRequestDTO bookingRequestDTO) {

        CustommerCommand custommerCommand =
            new CustommerCommand(bookingRequestDTO.getCustomerId(), restTemplate, secondServiceCallEnabled.get());

        return custommerCommand.queue();
    }

    private BookingServiceResponseDTO createBookingRequest(CustomerResponseDTO customerDTO, AddressResponseDTO senderAddressDTO,
            AddressResponseDTO receiverAddressDTO) {

        BookingServiceRequestDTO bookingServiceRequestDTO = new BookingServiceRequestDTO();
        bookingServiceRequestDTO.setReceiverAddress(receiverAddressDTO);
        bookingServiceRequestDTO.setSenderAddress(senderAddressDTO);
        bookingServiceRequestDTO.setCustomerDTO(customerDTO);

        if (customerDTO.isFallback() || senderAddressDTO.isFallback() || receiverAddressDTO.isFallback()) {
            BookingServiceResponseDTO bookingServiceResponseDTO = new BookingServiceResponseDTO();

            mapAddressCustomerResponse(customerDTO, senderAddressDTO, receiverAddressDTO, bookingServiceResponseDTO);

            bookingServiceResponseDTO.setFallback(false);

            return bookingServiceResponseDTO;
        } else {
            BookingCommand bookingCommand =
                new BookingCommand(bookingServiceRequestDTO, restTemplate, secondServiceCallEnabled.get());
            BookingServiceResponseDTO bookingServiceResponseDTO = bookingCommand.execute();

            mapAddressCustomerResponse(customerDTO, senderAddressDTO, receiverAddressDTO, bookingServiceResponseDTO);

            return bookingServiceResponseDTO;
        }

    }

    private void mapAddressCustomerResponse(CustomerResponseDTO customerDTO, AddressResponseDTO senderAddressDTO,
            AddressResponseDTO receiverAddressDTO, BookingServiceResponseDTO bookingServiceResponseDTO) {

        bookingServiceResponseDTO.setServiceResponseStatusList(new ArrayList<>());

        //@formatter:off
        if(bookingServiceResponseDTO.getConnoteDTO() == null) {
            bookingServiceResponseDTO.getServiceResponseStatusList()
                        .add(new ServiceResponseStatus("connote-service","ERROR","No Response", null));
        } else {
                bookingServiceResponseDTO.getServiceResponseStatusList()
                    .add(new ServiceResponseStatus("connote-service",
                            bookingServiceResponseDTO.getConnoteDTO().isFallback() ? "ERROR" : "OK",
                            bookingServiceResponseDTO.getConnoteDTO().getErrorMsg(),
                            bookingServiceResponseDTO.getConnoteDTO().getInstance()));
                }

        bookingServiceResponseDTO.getServiceResponseStatusList()
                .add(new ServiceResponseStatus("address-service > sender",
                        senderAddressDTO.isFallback() ? "ERROR" : "OK",
                        senderAddressDTO.getErrorMsg(),
                        senderAddressDTO.getInstance()));
        bookingServiceResponseDTO.getServiceResponseStatusList()
                .add(new ServiceResponseStatus("address-service > receiver",
                        receiverAddressDTO.isFallback() ? "ERROR" : "OK",
                        receiverAddressDTO.getErrorMsg(),
                        receiverAddressDTO.getInstance()));
        bookingServiceResponseDTO.getServiceResponseStatusList()
                .add(new ServiceResponseStatus("customer-service",
                        customerDTO.isFallback() ? "ERROR" : "OK",
                        customerDTO.getErrorMsg(),
                        customerDTO.getInstance()));

        //@formatter:on
    }

    public BookingServiceResponseDTO executeBookingRequest(BookingRequestDTO bookingRequestDTO) {
        // 1.) check customer
        Future<CustomerResponseDTO> customerRequestFuture = checkCustomer(bookingRequestDTO);

        // 2.) check sender address
        Future<AddressResponseDTO> senderAddressFuture = checkAddress(exctractSenderAddress(bookingRequestDTO));

        // 3.) check receiver address
        Future<AddressResponseDTO> receiverAddressFuture = checkAddress(exctractReceiverAddress(bookingRequestDTO));

        CustomerResponseDTO customerResponseDTO;
        AddressResponseDTO senderAddressResponseDTO;
        AddressResponseDTO receiverAddressResponseDTO;
        try {
            customerResponseDTO = customerRequestFuture.get();
            senderAddressResponseDTO = senderAddressFuture.get();
            receiverAddressResponseDTO = receiverAddressFuture.get();

        } catch (InterruptedException e) {
            String msg = "Interrupted Exception -  while receive future";
            LOGGER.debug(msg, e);
            return createBookingResponseFallback(msg, e);
        } catch (ExecutionException e) {
            String msg = "Execution Exception -  while receive future";
            LOGGER.debug(msg, e);
            return createBookingResponseFallback(msg, e);
        }

        // 4.) create booking with
        return createBookingRequest(customerResponseDTO, senderAddressResponseDTO, receiverAddressResponseDTO);
    }

    private BookingServiceResponseDTO createBookingResponseFallback(String msg, Exception e) {

        BookingServiceResponseDTO bookingServiceResponseDTO = new BookingServiceResponseDTO();
        bookingServiceResponseDTO.setErrorMsg(msg + ": " + e.getMessage());
        return bookingServiceResponseDTO;
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
