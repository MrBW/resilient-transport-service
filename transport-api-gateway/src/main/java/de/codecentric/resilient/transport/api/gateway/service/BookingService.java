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
import de.codecentric.resilient.transport.api.gateway.dto.TransportApiGatewayResponse;

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

    private TransportApiGatewayResponse createBookingRequest(CustomerResponseDTO customerDTO,
            AddressResponseDTO senderAddressDTO, AddressResponseDTO receiverAddressDTO) {

        // TODO: do-it-better
        TransportApiGatewayResponse transportApiResponse = new TransportApiGatewayResponse();

        BookingServiceRequestDTO bookingServiceRequestDTO = new BookingServiceRequestDTO();
        bookingServiceRequestDTO.setReceiverAddress(receiverAddressDTO);
        bookingServiceRequestDTO.setSenderAddress(senderAddressDTO);
        bookingServiceRequestDTO.setCustomerDTO(customerDTO);

        if (customerDTO.isFallback() || senderAddressDTO.isFallback() || receiverAddressDTO.isFallback()) {

            mapAddressCustomerResponse(customerDTO, senderAddressDTO, receiverAddressDTO, null, transportApiResponse);

            transportApiResponse.setFallback(true);
            transportApiResponse.setErrorMsg("Invalid booking data");

            return transportApiResponse;
        } else {
            BookingCommand bookingCommand =
                new BookingCommand(bookingServiceRequestDTO, restTemplate, secondServiceCallEnabled.get());
            BookingServiceResponseDTO bookingServiceResponseDTO = bookingCommand.execute();

            mapAddressCustomerResponse(customerDTO, senderAddressDTO, receiverAddressDTO, bookingServiceResponseDTO,
                transportApiResponse);

            if (bookingServiceResponseDTO.isFallback()) {
                transportApiResponse.setFallback(true);
                transportApiResponse.setErrorMsg(bookingServiceResponseDTO.getErrorMsg());

            } else {
                transportApiResponse.setCustomerDTO(bookingServiceResponseDTO.getCustomerDTO());
                transportApiResponse.setConnoteDTO(bookingServiceResponseDTO.getConnoteDTO());
                transportApiResponse.setFallback(false);
            }

            return transportApiResponse;
        }

    }

    private void mapAddressCustomerResponse(CustomerResponseDTO customerDTO, AddressResponseDTO senderAddressDTO,
            AddressResponseDTO receiverAddressDTO, BookingServiceResponseDTO bookingServiceResponseDTO,
            TransportApiGatewayResponse transportApiResponse) {

        transportApiResponse.setServiceResponseStatusList(new ArrayList<>());

        // TODO: REFCATORING!!!

        //@formatter:off
        // Booking Service
        if(bookingServiceResponseDTO != null) {
        transportApiResponse.getServiceResponseStatusList()
                .add(new ServiceResponseStatus("booking-service",
                            bookingServiceResponseDTO.isFallback() ? "ERROR" : "OK",
                            bookingServiceResponseDTO.getErrorMsg()));
        }
        // Connote Details
        if(bookingServiceResponseDTO == null || bookingServiceResponseDTO.getConnoteDTO() == null) {
            transportApiResponse.getServiceResponseStatusList()
                        .add(new ServiceResponseStatus("connote-service","ERROR","No Response"));
        } else {
                transportApiResponse.getServiceResponseStatusList()
                    .add(new ServiceResponseStatus("connote-service",
                            bookingServiceResponseDTO.getConnoteDTO().isFallback() ? "ERROR" : "OK",
                            bookingServiceResponseDTO.getConnoteDTO().getErrorMsg()));
                }

        // Address Service Sender
        transportApiResponse.getServiceResponseStatusList()
                .add(new ServiceResponseStatus("address-service > sender",
                        senderAddressDTO.isFallback() ? "ERROR" : "OK",
                        senderAddressDTO.getErrorMsg()));

        // Address Service Receiver
        transportApiResponse.getServiceResponseStatusList()
                .add(new ServiceResponseStatus("address-service > receiver",
                        receiverAddressDTO.isFallback() ? "ERROR" : "OK",
                        receiverAddressDTO.getErrorMsg()));

        // Customer Service
        transportApiResponse.getServiceResponseStatusList()
                .add(new ServiceResponseStatus("customer-service",
                        customerDTO.isFallback() ? "ERROR" : "OK",
                        customerDTO.getErrorMsg()));

        //@formatter:on
    }

    public TransportApiGatewayResponse executeBookingRequest(BookingRequestDTO bookingRequestDTO) {
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

    private TransportApiGatewayResponse createBookingResponseFallback(String msg, Exception e) {

        TransportApiGatewayResponse transportApiGatewayResponse = new TransportApiGatewayResponse();
        transportApiGatewayResponse.setErrorMsg(msg + ": " + e.getMessage());
        return transportApiGatewayResponse;
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
