package com.codecentric.de.resilient.booking.mapper;

import com.codecentric.de.resilient.dto.BookingRequestDTO;
import org.springframework.stereotype.Component;
import com.codecentric.de.resilient.booking.entity.Address;
import com.codecentric.de.resilient.booking.entity.Booking;
import com.codecentric.de.resilient.booking.entity.Customer;
import com.codecentric.de.resilient.dto.BookingResponseDTO;
import com.codecentric.de.resilient.dto.CustomerDTO;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Component
public class BookingMapper {

    public Booking mapToBookingEntity(BookingRequestDTO bookingRequestDTO, Long connote) {
        Booking booking = new Booking();
        booking.setConnote(connote);
        booking.setReceiverAddress(mapToAddressEntity(booking.getReceiverAddress()));
        booking.setSenderAddress(mapToAddressEntity(booking.getSenderAddress()));
        booking.setCustomer(mapToCustomer(bookingRequestDTO.getCustomerDTO()));

        return booking;
    }

    public Customer mapToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setCustomerId(customerDTO.getCustomerId());

        return customer;
    }

    public Address mapToAddressEntity(Address addressDTO) {

        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setCountry(addressDTO.getCountry());
        address.setPostcode(addressDTO.getPostcode());
        address.setCity(addressDTO.getCity());
        address.setStreetNumber(addressDTO.getStreetNumber());
        return address;
    }
}
