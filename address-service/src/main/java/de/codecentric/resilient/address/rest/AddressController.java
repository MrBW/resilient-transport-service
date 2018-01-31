package de.codecentric.resilient.address.rest;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import de.codecentric.resilient.address.entity.Address;
import de.codecentric.resilient.address.service.AddressService;
import de.codecentric.resilient.dto.AddressDTO;

/**
 * @author Benjamin Wilms
 */
@RestController
@RequestMapping("rest/address")
public class AddressController {

    private final AddressService addressService;
    private final Tracer tracer;



    @Autowired
    public AddressController(AddressService addressService, Tracer tracer) {
        this.addressService = addressService;
        this.tracer = tracer;
    }

    @RequestMapping(value = "validate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AddressDTO> validateAdress(@RequestBody AddressDTO addressDTO, HttpServletRequest request) {

        Address address;
        try {
            appendSpan("not-found");
            address = addressService.validateAddress(addressDTO);
        } catch (Exception e) {
            appendSpan("not-found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (address == null) {
            appendSpan("not-found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AddressDTO adressDTO = mapToAdressDTO(address);

        appendSpan(address.getCity());

        return new ResponseEntity<>(adressDTO, HttpStatus.FOUND);

    }

    private void appendSpan(String city) {
        Span span = tracer.getCurrentSpan();
        String baggageKey = "address-city";
        String baggageValue = city;
        span.setBaggageItem(baggageKey, baggageValue);
        tracer.addTag(baggageKey, baggageValue);
    }

    @RequestMapping(value = "generate")
    @ResponseBody
    public ResponseEntity<AddressDTO> addressDTO(HttpServletRequest request) {

        return new ResponseEntity<>(new AddressDTO("", "", "", "", ""), HttpStatus.OK);
    }

    /***
     * Erzeuge valides AddressDTO
     * @param address Address
     * @return AddressDTO
     */
    private AddressDTO mapToAdressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity(address.getCity());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setPostcode(address.getPostcode());
        addressDTO.setStreet(address.getStreet());

        return addressDTO;
    }
}
