package de.codecentric.resilient.transport.api.gateway.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import de.codecentric.resilient.transport.api.gateway.dto.BookingRequestDTO;
import de.codecentric.resilient.transport.api.gateway.service.BookingService;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Service
public class AsyncBooking {

    private final BookingService bookingService;

    @Autowired
    public AsyncBooking(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Async
    public void createAsyncRequest(Integer timer, BookingRequestDTO bookingRequestDTO) {
        long t = System.currentTimeMillis();
        long end = t + timer;

        while (System.currentTimeMillis() < end) {
            try {

                bookingService.executeBookingRequest(bookingRequestDTO);
                bookingService.executeBookingRequest(bookingRequestDTO);

                Thread.sleep(5);
            } catch (InterruptedException e) {
                // Ignore
            }
        }
    }

}
