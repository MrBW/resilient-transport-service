package com.codecentric.de.resilient.address.rest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.codecentric.de.resilient.address.entity.Address;
import com.codecentric.de.resilient.address.service.AddressService;
import com.codecentric.de.resilient.dto.AddressDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Benjamin Wilms (xd98870)
 */

@RunWith(MockitoJUnitRunner.class)
public class AddressControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AddressService addressServiceMock;

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(new AddressController(addressServiceMock)).build();

    }

    @Test
    public void validateAdress_Found() throws Exception {

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("Hochstraße");
        addressDTO.setStreetNumber("11");
        addressDTO.setCity("Solingen");
        addressDTO.setPostcode("42697");
        addressDTO.setCountry("DE");

        Address address = new Address(addressDTO.getCountry(), addressDTO.getCity(), addressDTO.getPostcode(),
            addressDTO.getStreet(), addressDTO.getStreetNumber());
        when(addressServiceMock.validateAddress(any(AddressDTO.class))).thenReturn(address);

    //@formatter:off
        mockMvc.perform(post("/rest/address/validate")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonStringFromObject(addressDTO))
        )
        .andExpect(status().isFound());

        //@formatter:on

    }

    @Test
    public void validateAdress_NotFound() throws Exception {

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("Hochstraße");
        addressDTO.setStreetNumber("11");
        addressDTO.setCity("Solingen");
        addressDTO.setPostcode("42697");
        addressDTO.setCountry("DE");

        when(addressServiceMock.validateAddress(any(AddressDTO.class))).thenReturn(null);

    //@formatter:off
        mockMvc.perform(post("/rest/address/validate")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonStringFromObject(addressDTO))
        )
        .andExpect(status().isNotFound());

        //@formatter:on

    }

    @Test
    public void validateAdress_Exception() throws Exception {

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("Hochstraße");
        addressDTO.setStreetNumber("11");
        addressDTO.setCity("Solingen");
        addressDTO.setPostcode("42697");
        addressDTO.setCountry("DE");

        when(addressServiceMock.validateAddress(any(AddressDTO.class))).thenThrow(new RuntimeException("Error"));

    //@formatter:off
        mockMvc.perform(post("/rest/address/validate")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonStringFromObject(addressDTO))
        )
        .andExpect(status().isNotFound());

        //@formatter:on

    }

    private String jsonStringFromObject(AddressDTO addressDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(addressDTO);
    }

}