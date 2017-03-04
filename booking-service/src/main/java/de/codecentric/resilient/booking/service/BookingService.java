package de.codecentric.resilient.booking.service;

import java.util.Date;

import de.codecentric.resilient.booking.entity.Booking;
import de.codecentric.resilient.booking.mapper.BookingMapper;
import de.codecentric.resilient.booking.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import de.codecentric.resilient.dto.BookingServiceRequestDTO;
import de.codecentric.resilient.dto.BookingServiceResponseDTO;
import de.codecentric.resilient.dto.ConnoteDTO;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * @author Benjamin Wilms
 */
@Service
public class BookingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final EurekaClient discoveryClient;

    @Autowired
    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper, EurekaClient discoveryClient) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.discoveryClient = discoveryClient;
    }

    public BookingServiceResponseDTO createBooking(BookingServiceRequestDTO bookingRequestDTO) {

        BookingServiceResponseDTO bookingResponseDTO = new BookingServiceResponseDTO();

        // 1.) Create connote
        ConnoteDTO connoteDTO = receiveConnote();

        // 2. Save booking request

        Booking booking = bookingMapper.mapToBookingEntity(bookingRequestDTO, connoteDTO.getConnote());
        bookingRepository.save(booking);

        bookingResponseDTO.setCreated(new Date());
        bookingResponseDTO.setCustomerDTO(bookingRequestDTO.getCustomerDTO());
        bookingResponseDTO.setConnoteDTO(connoteDTO);

        return bookingResponseDTO;

    }

    @HystrixCommand(groupKey = "ConnoteServiceClientGroup", fallbackMethod = "fallbackGetConnote")
    private ConnoteDTO receiveConnote() {
        LOGGER.debug("Starting getConnote  (1)");

        return getConnoteDTO();
    }

    @HystrixCommand(groupKey = "ConnoteServiceClientGroup", fallbackMethod = "fallbackGetConnoteFinal")
    private ConnoteDTO fallbackGetConnote() {

        LOGGER.debug("Fallback - Starting getConnote  (2)");
        return getConnoteDTO();

    }

    private ConnoteDTO fallbackGetConnoteFinal(Throwable throwable) {
        ConnoteDTO connoteDTO = new ConnoteDTO();
        connoteDTO.setConnote(null);
        connoteDTO.setCreated(null);

        // Exception wrapping
        connoteDTO.setFallback(true);
        connoteDTO.setErrorMsg(throwable == null ? "Error not reachable" : throwable.getMessage());

        return connoteDTO;
    }

    private ConnoteDTO getConnoteDTO() {
        InstanceInfo instanceInfo = discoveryClient.getNextServerFromEureka("CONNOTE-SERVICE", false);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(instanceInfo.getHomePageUrl() + "rest/connote/create", ConnoteDTO.class);
    }
}
