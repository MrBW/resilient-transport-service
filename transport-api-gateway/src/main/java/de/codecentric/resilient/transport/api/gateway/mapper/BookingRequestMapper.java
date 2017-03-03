package de.codecentric.resilient.transport.api.gateway.mapper;

import org.springframework.stereotype.Component;
import de.codecentric.resilient.dto.AddressDTO;
import de.codecentric.resilient.dto.AddressResponseDTO;
import de.codecentric.resilient.dto.BookingServiceRequestDTO;
import de.codecentric.resilient.dto.CustomerDTO;
import de.codecentric.resilient.transport.api.gateway.dto.CustomerResponseDTO;

/**
 * @author Benjamin Wilms
 */
@Component
public class BookingRequestMapper {

    public AddressDTO mapAddressResponseToAddressDTO(AddressResponseDTO addressResponseDTO) {

        return new AddressDTO(addressResponseDTO.getCountry(), addressResponseDTO.getCity(), addressResponseDTO.getPostcode(),
            addressResponseDTO.getStreet(), addressResponseDTO.getStreetNumber());
    }

    public CustomerDTO mapCustomerResponseToCustomerDTO(CustomerResponseDTO customerResponseDTO) {
        return new CustomerDTO(customerResponseDTO.getCustomerId(), customerResponseDTO.getCustomerName());
    }

    public BookingServiceRequestDTO mapBookingRequestToBookingServiceRequest(CustomerDTO customerDTO,
            AddressDTO senderAddressDTO, AddressDTO receiverAddressDTO) {

        BookingServiceRequestDTO bookingServiceRequestDTO = new BookingServiceRequestDTO();
        bookingServiceRequestDTO.setSenderAddress(senderAddressDTO);
        bookingServiceRequestDTO.setReceiverAddress(receiverAddressDTO);
        bookingServiceRequestDTO.setCustomerDTO(customerDTO);

        return bookingServiceRequestDTO;
    }
}
