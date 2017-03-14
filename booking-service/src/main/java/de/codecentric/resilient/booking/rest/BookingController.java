package de.codecentric.resilient.booking.rest;

import javax.servlet.http.HttpServletRequest;

import de.codecentric.resilient.dto.AddressDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import de.codecentric.resilient.booking.service.BookingService;
import de.codecentric.resilient.dto.BookingServiceRequestDTO;
import de.codecentric.resilient.dto.BookingServiceResponseDTO;
import de.codecentric.resilient.dto.CustomerDTO;

/**
 * @author Benjamin Wilms
 */
@RestController
@RequestMapping("rest/booking")
public class BookingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BookingServiceResponseDTO> create(@RequestBody BookingServiceRequestDTO bookingRequestDTO,
                                                            HttpServletRequest request) {

        LOGGER.debug(LOGGER.isDebugEnabled() ? "BookingRequest: " + bookingRequestDTO.toString() : null);

        BookingServiceResponseDTO booking = bookingService.createBooking(bookingRequestDTO);

        return new ResponseEntity<>(booking, HttpStatus.OK);
    }


}
