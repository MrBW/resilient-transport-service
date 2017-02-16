package com.codecentric.de.address.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codecentric.de.address.entity.Address;
import com.codecentric.de.address.service.AddressService;
import com.codecentric.de.resilient.dto.AddressDTO;

/**
 * @author Benjamin Wilms
 */
@RestController
@RequestMapping("rest/address")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(value = "validate", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AddressDTO> validateAdress(@RequestBody AddressDTO addressDTO, HttpServletRequest request) {

        Address address;
        try {
            address = addressService.validateAddress(addressDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (address == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AddressDTO adressDTO = mapToAdressDTO(address);
        addressDTO.setInstance(request.getLocalName() + " : " + request.getLocalPort());

        return new ResponseEntity<>(adressDTO, HttpStatus.FOUND);

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
