package de.codecentric.resilient.booking.service;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicPropertyFactory;
import de.codecentric.resilient.booking.commands.ConnoteCommand;
import de.codecentric.resilient.booking.entity.Booking;
import de.codecentric.resilient.booking.mapper.BookingMapper;
import de.codecentric.resilient.booking.repository.BookingRepository;
import de.codecentric.resilient.dto.*;

/**
 * @author Benjamin Wilms
 */
@Service
public class BookingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final RestTemplate restTemplate;

    private DynamicBooleanProperty secondServiceCallEnabled =
        DynamicPropertyFactory.getInstance().getBooleanProperty("second.service.call", false);

    @Autowired
    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper, RestTemplate restTemplate) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.restTemplate = restTemplate;
    }

    public BookingServiceResponseDTO createBooking(BookingServiceRequestDTO bookingRequestDTO) {

        BookingServiceResponseDTO bookingResponseDTO = new BookingServiceResponseDTO();

        // 1.) Create connote
        ConnoteDTO connoteDTO = receiveConnote();

        if (connoteDTO.isFallback()) {
            bookingResponseDTO.setErrorMsg("Connote error: " + connoteDTO.getErrorMsg());
            bookingResponseDTO.setStatus("ERROR");
            return bookingResponseDTO;
        }

        // 2. Save booking request

        Booking booking = bookingMapper.mapToBookingEntity(bookingRequestDTO, connoteDTO.getConnote());
        bookingRepository.save(booking);

        CustomerResponseDTO customerDTO = bookingRequestDTO.getCustomerDTO();
        bookingResponseDTO.setCustomerDTO(new CustomerDTO(customerDTO.getCustomerId(), customerDTO.getCustomerName()));
        bookingResponseDTO.setConnoteDTO(connoteDTO);
        bookingResponseDTO.setStatus("OK");

        return bookingResponseDTO;

    }

    private ConnoteDTO receiveConnote() {
        LOGGER.debug("Starting getConnote");

        return new ConnoteCommand(restTemplate, secondServiceCallEnabled.get()).execute();
    }
}
