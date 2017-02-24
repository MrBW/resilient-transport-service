package de.codecentric.resilient.address.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import de.codecentric.resilient.address.entity.Address;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import de.codecentric.resilient.address.repository.AddressRepository;
import de.codecentric.resilient.dto.AddressDTO;

/**
 * @author Benjamin Wilms (xd98870)
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressServiceIntegrationTest {

    @Autowired
    private AddressRepository addressRepository;

    private AddressService addressService;

    @Before
    public void setUp() throws Exception {

        addressRepository.deleteAll();

        addressRepository.save(new Address("DE", "Solingen", "42697", "Hochstraße", "11"));
        addressRepository.save(new Address("DE", "Berlin", "10785", "Kemperplatz", "1"));
        addressRepository.save(new Address("DE", "Dortmund", "44137", "Hoher Wall", "15"));
        addressRepository.save(new Address("DE", "Düsseldorf", "40591", "Kölner Landstraße", "11"));
        addressRepository.save(new Address("DE", "Frankfurt", "60486", "Kreuznacher Straße", "30"));
        addressRepository.save(new Address("DE", "Hamburg", "22767", "Große Elbstraße", "14"));
        addressRepository.save(new Address("DE", "Karlsruhe", "76135", "Gartenstraße", "69a"));
        addressRepository.save(new Address("DE", "München", "80687", "Elsenheimerstraße", "55a"));
        addressRepository.save(new Address("DE", "Münster", "48167", "Wolbecker Windmühle", "29j"));
        addressRepository.save(new Address("DE", "Nürnberg", "90403", "Josephsplatz", "8"));
        addressRepository.save(new Address("DE", "Stuttgart", "70563", "Curiesstraße", "2"));

        addressService = new AddressService(addressRepository);
    }

    @After
    public void tearDown() throws Exception {

        addressRepository.deleteAll();

    }

    @Test
    public void count() throws Exception {
        assertThat(addressRepository.count(), is(11L));

    }

    @Test
    public void validateAddress() throws Exception {
        AddressDTO addressDto = createAddressDto();

        Address address = addressService.validateAddress(addressDto);

        assertThat(address.getCity(), is(addressDto.getCity()));
        assertThat(address.getCountry(), is(addressDto.getCountry()));
        assertThat(address.getPostcode(), is(addressDto.getPostcode()));
        assertThat(address.getStreet(), is(addressDto.getStreet()));
        assertThat(address.getStreetNumber(), is(addressDto.getStreetNumber()));

    }

    @Test
    public void validateAddress_AddressLike() throws Exception {
        AddressDTO addressDto = createAddressDto();
        addressDto.setStreet("Hochstr");
        addressDto.setCity("Soling");

        Address address = addressService.validateAddress(addressDto);

        assertThat(address.getCity(), is("Solingen"));
        assertThat(address.getCountry(), is(addressDto.getCountry()));
        assertThat(address.getPostcode(), is(addressDto.getPostcode()));
        assertThat(address.getStreet(), is("Hochstraße"));
        assertThat(address.getStreetNumber(), is(addressDto.getStreetNumber()));

    }

    private AddressDTO createAddressDto() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("Hochstraße");
        addressDTO.setStreetNumber("11");
        addressDTO.setCity("Solingen");
        addressDTO.setPostcode("42697");
        addressDTO.setCountry("DE");

        return addressDTO;
    }

}