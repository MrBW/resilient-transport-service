package de.codecentric.resilient.transport.api.gateway.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import de.codecentric.resilient.dto.BookingServiceRequestDTO;
import de.codecentric.resilient.dto.BookingServiceResponseDTO;

/**
 * @author Benjamin Wilms
 */
public class BookingCommand extends HystrixCommand<BookingServiceResponseDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingCommand.class);

    private final BookingServiceRequestDTO bookingServiceRequestDTO;

    private final RestTemplate restTemplate;

    private final boolean secondTry;

    public BookingCommand(BookingServiceRequestDTO bookingServiceRequestDTO, RestTemplate restTemplate, boolean secondTry) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("BookingServiceClientGroup"))
            .andCommandKey(HystrixCommandKey.Factory.asKey("BookingServiceClient")));

        this.bookingServiceRequestDTO = bookingServiceRequestDTO;
        this.restTemplate = restTemplate;
        this.secondTry = secondTry;
    }

    @Override
    protected BookingServiceResponseDTO run() throws Exception {
        return restTemplate.postForObject("http://booking-service/rest/booking/create", bookingServiceRequestDTO,
            BookingServiceResponseDTO.class);
    }

    @Override
    protected BookingServiceResponseDTO getFallback() {

        if (secondTry) {
            LOGGER.debug(LOGGER.isDebugEnabled() ? "Second Booking Service Call started" : null);

            return new BookingCommand(bookingServiceRequestDTO, restTemplate, false).execute();

        } else {

            BookingServiceResponseDTO bookingServiceResponseDTO = new BookingServiceResponseDTO();
            bookingServiceResponseDTO.setFallback(true);
            if (getFailedExecutionException() == null)
                bookingServiceResponseDTO.setErrorMsg("Error: unable to create booking");
            else
                bookingServiceResponseDTO
                    .setErrorMsg("Error: unable to create booking - " + getFailedExecutionException().getMessage());
            return bookingServiceResponseDTO;
        }
    }
}
