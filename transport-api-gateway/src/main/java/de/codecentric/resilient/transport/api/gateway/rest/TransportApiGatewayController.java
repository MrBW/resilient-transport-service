package de.codecentric.resilient.transport.api.gateway.rest;

import javax.servlet.http.HttpServletRequest;

import de.codecentric.resilient.transport.api.gateway.async.AsyncBooking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import de.codecentric.resilient.dto.BookingServiceResponseDTO;
import de.codecentric.resilient.transport.api.gateway.dto.BookingRequestDTO;
import de.codecentric.resilient.transport.api.gateway.service.BookingService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Benjamin Wilms
 */
@RestController
@RequestMapping("rest/transport/booking")
public class TransportApiGatewayController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportApiGatewayController.class);

    private final BookingService bookingService;
    private final AsyncBooking asyncBooking;

    @Autowired
    public TransportApiGatewayController(BookingService bookingService, AsyncBooking asyncBooking) {
        this.bookingService = bookingService;
        this.asyncBooking = asyncBooking;
    }

    @RequestMapping(value = "simulate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> simulateBooking(@RequestParam(name = "ms") Integer timer,
            @RequestBody BookingRequestDTO bookingRequestDTO) {

        if (timer == null) {
            return new ResponseEntity<String>("Request-Param ms == null", HttpStatus.BAD_REQUEST);
        } else {
            asyncBooking.createAsyncRequest(timer, bookingRequestDTO);

            LOGGER.debug(LOGGER.isDebugEnabled() ? "Stopping booking simulator after " + timer + "ms" : null);

            Calendar runningUntil = Calendar.getInstance();
            runningUntil.add(Calendar.MILLISECOND, timer);


            SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");

            return new ResponseEntity<String>("Runs until > " + dateFormatter.format(runningUntil.getTime()), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BookingServiceResponseDTO> create(@RequestBody BookingRequestDTO bookingRequestDTO,
            HttpServletRequest request) {

        LOGGER.debug(LOGGER.isDebugEnabled() ? "Booking Request: " + bookingRequestDTO.toString() : null);

        BookingServiceResponseDTO responseDTO = bookingService.executeBookingRequest(bookingRequestDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "generate")
    public ResponseEntity<BookingRequestDTO> generate(HttpServletRequest request) {

        return new ResponseEntity<>(new BookingRequestDTO(), HttpStatus.OK);
    }

}
