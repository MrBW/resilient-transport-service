package com.codecentric.de.resilient.booking.rest;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codecentric.de.resilient.booking.service.BookingService;
import com.codecentric.de.resilient.dto.AddressDTO;
import com.codecentric.de.resilient.dto.BookingRequestDTO;
import com.codecentric.de.resilient.dto.BookingResponseDTO;
import com.codecentric.de.resilient.dto.CustomerDTO;

/**
 * @author Benjamin Wilms
 */
@RestController
@RequestMapping("rest/booking")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BookingResponseDTO> create(@RequestBody BookingRequestDTO bookingRequestDTO,
            HttpServletRequest request) {
        BookingResponseDTO booking = bookingService.createBooking(bookingRequestDTO);

        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @RequestMapping(value = "generate")
    public ResponseEntity<BookingRequestDTO> generate(HttpServletRequest request) {
        BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
        bookingRequestDTO.setReceiverAddress(new AddressDTO("", "", "", "", ""));
        bookingRequestDTO.setSenderAddress(new AddressDTO("", "", "", "", ""));
        bookingRequestDTO.setCustomerDTO(new CustomerDTO(0L, ""));

        return new ResponseEntity<>(bookingRequestDTO, HttpStatus.OK);
    }
}
