package de.codecentric.resilient.transport.api.gateway.commands;

import com.netflix.hystrix.HystrixCommandKey;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import de.codecentric.resilient.dto.BookingServiceRequestDTO;
import de.codecentric.resilient.dto.BookingServiceResponseDTO;

/**
 * @author Benjamin Wilms
 */
public class BookingCommand extends HystrixCommand<BookingServiceResponseDTO> {

    private final BookingServiceRequestDTO bookingServiceRequestDTO;

    private final RestTemplate restTemplate;

    public BookingCommand(BookingServiceRequestDTO bookingServiceRequestDTO, RestTemplate restTemplate) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("BookingServiceClientGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("BookingServiceClient")));


        this.bookingServiceRequestDTO = bookingServiceRequestDTO;
        this.restTemplate = restTemplate;
    }

    @Override
    protected BookingServiceResponseDTO run() throws Exception {
        return restTemplate.postForObject("http://booking-service/rest/booking/create", bookingServiceRequestDTO,
            BookingServiceResponseDTO.class);
    }

    @Override
    protected BookingServiceResponseDTO getFallback() {

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
