package de.codecentric.resilient.booking.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;

import de.codecentric.resilient.booking.service.BookingService;
import de.codecentric.resilient.dto.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Benjamin Wilms (xd98870)
 */
@RunWith(MockitoJUnitRunner.class)
public class BookingControllerTest {
    @Test
    public void create1() throws Exception {

    }

    private MockMvc mockMvc;

    @Mock
    private BookingService bookingServiceMock;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new BookingController(bookingServiceMock)).build();
    }

    @Test
    public void create() throws Exception {
        BookingServiceRequestDTO bookingRequestDTO = new BookingServiceRequestDTO();
        bookingRequestDTO.setCustomerDTO(createCustomerDTO());
        bookingRequestDTO.setReceiverAddress(createAddress());
        bookingRequestDTO.setSenderAddress(createAddress());

        BookingServiceResponseDTO bookingResponseDTO = new BookingServiceResponseDTO();
        ConnoteDTO connoteDTO = new ConnoteDTO();
        connoteDTO.setConnote(123L);
        bookingResponseDTO.setConnoteDTO(connoteDTO);
        bookingResponseDTO.setCreated(java.sql.Date.valueOf(LocalDate.of(2017,02,01)));

        when(bookingServiceMock.createBooking(bookingRequestDTO)).thenReturn(bookingResponseDTO);

        //@formatter:off
        mockMvc.perform(post("/rest/booking/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonStringFromObject(bookingRequestDTO))
        )
        .andExpect(status().isOk());

        //@formatter:on
    }

    private AddressDTO createAddress() {

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("Hochstraße");
        addressDTO.setStreetNumber("11");
        addressDTO.setCity("Solingen");
        addressDTO.setPostcode("42697");
        addressDTO.setCountry("DE");
        return addressDTO;
    }

    private CustomerDTO createCustomerDTO() {
        return new CustomerDTO(1L, "Meier");

    }

    private String jsonStringFromObject(BookingServiceRequestDTO bookingRequestDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(bookingRequestDTO);
    }

}