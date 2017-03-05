package de.codecentric.resilient.booking.service;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import de.codecentric.resilient.booking.commands.ConnoteCommand;
import de.codecentric.resilient.booking.entity.Booking;
import de.codecentric.resilient.booking.mapper.BookingMapper;
import de.codecentric.resilient.booking.repository.BookingRepository;
import de.codecentric.resilient.dto.BookingServiceRequestDTO;
import de.codecentric.resilient.dto.BookingServiceResponseDTO;
import de.codecentric.resilient.dto.ConnoteDTO;

/**
 * @author Benjamin Wilms
 */
@Service
public class BookingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final RestTemplate restTemplate;

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
            bookingResponseDTO.setErrorMsg("Unable to create Connote - " + connoteDTO.getErrorMsg());
            bookingResponseDTO.setCreated(null);
            return bookingResponseDTO;
        }

        // 2. Save booking request

        Booking booking = bookingMapper.mapToBookingEntity(bookingRequestDTO, connoteDTO.getConnote());
        bookingRepository.save(booking);

        bookingResponseDTO.setCreated(new Date());
        bookingResponseDTO.setCustomerDTO(bookingRequestDTO.getCustomerDTO());
        bookingResponseDTO.setConnoteDTO(connoteDTO);

        return bookingResponseDTO;

    }

    private ConnoteDTO receiveConnote() {
        LOGGER.debug("Starting getConnote  (1)");

        ConnoteDTO connoteDTO = new ConnoteCommand(restTemplate).execute();

        if (connoteDTO.isFallback()) {
            LOGGER.debug("Fallback - Starting getConnote  (2)");
            connoteDTO = new ConnoteCommand(restTemplate).execute();
        }

        return connoteDTO;
    }
}
