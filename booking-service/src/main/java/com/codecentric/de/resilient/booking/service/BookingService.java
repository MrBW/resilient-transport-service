package com.codecentric.de.resilient.booking.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.codecentric.de.resilient.booking.entity.Booking;
import com.codecentric.de.resilient.booking.mapper.BookingMapper;
import com.codecentric.de.resilient.booking.repository.BookingRepository;
import com.codecentric.de.resilient.dto.BookingRequestDTO;
import com.codecentric.de.resilient.dto.BookingResponseDTO;
import com.codecentric.de.resilient.dto.ConnoteDTO;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * @author Benjamin Wilms
 */
@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final EurekaClient discoveryClient;

    @Autowired
    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper, EurekaClient discoveryClient) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.discoveryClient = discoveryClient;
    }

    public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO) {

        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();

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

    @HystrixCommand(fallbackMethod = "fallbackOneBooking")
    private ConnoteDTO receiveConnote() {
        return getConnoteDTO();
    }

    @HystrixCommand(fallbackMethod = "fallbackFinalBooking")
    private ConnoteDTO fallbackOneBooking() {

        return getConnoteDTO();

    }

    private ConnoteDTO fallbackFinalBooking(Throwable throwable) {
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
