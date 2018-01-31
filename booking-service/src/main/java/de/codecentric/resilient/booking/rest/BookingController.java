package de.codecentric.resilient.booking.rest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import de.codecentric.resilient.booking.service.BookingService;
import de.codecentric.resilient.dto.BookingServiceRequestDTO;
import de.codecentric.resilient.dto.BookingServiceResponseDTO;

/**
 * @author Benjamin Wilms
 */
@RestController
@RequestMapping("rest/booking")
public class BookingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;
    private final Tracer tracer;

    @Autowired
    public BookingController(BookingService bookingService, Tracer tracer) {
        this.bookingService = bookingService;
        this.tracer = tracer;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BookingServiceResponseDTO> create(@RequestBody BookingServiceRequestDTO bookingRequestDTO,
                                                            HttpServletRequest request) {

        LOGGER.debug(LOGGER.isDebugEnabled() ? "BookingRequest: " + bookingRequestDTO.toString() : null);

        BookingServiceResponseDTO booking = bookingService.createBooking(bookingRequestDTO);

        appendSpan(booking == null || booking.getStatus() == null ? "ERROR" : booking.getStatus(),"booking-status");

        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    private void appendSpan(String value, String key) {
        Span span = tracer.getCurrentSpan();
        String baggageKey = key;
        String baggageValue = value;
        span.setBaggageItem(baggageKey, baggageValue);
        tracer.addTag(baggageKey, baggageValue);
    }


}
