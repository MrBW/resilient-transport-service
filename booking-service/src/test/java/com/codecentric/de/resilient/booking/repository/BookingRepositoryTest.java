package com.codecentric.de.resilient.booking.repository;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.codecentric.de.resilient.booking.entity.Address;
import com.codecentric.de.resilient.booking.entity.Booking;
import com.codecentric.de.resilient.booking.entity.Customer;

/**
 * @author Benjamin Wilms (xd98870)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void checkSaveBooking() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerName("Meier");

        Address senderAddress = createAddress();
        Address receiverAddress = createAddress();

        Booking booking = new Booking();
        booking.setConnote(992233L);
        booking.setCustomer(customer);
        booking.setReceiverAddress(receiverAddress);
        booking.setSenderAddress(senderAddress);

        Booking savedBooking = bookingRepository.save(booking);

        assertThat(bookingRepository.count(), is(1L));
        assertThat(savedBooking, notNullValue());
        assertThat(savedBooking.getConnote(), is(booking.getConnote()));
        assertThat(savedBooking.getCustomer(), is(booking.getCustomer()));
        assertThat(savedBooking.getReceiverAddress(), notNullValue());
        assertThat(savedBooking.getSenderAddress(), notNullValue());
        assertThat(savedBooking.getId(), notNullValue());

    }

    private Address createAddress() {
        Address senderAddress = new Address();
        senderAddress.setCity(RandomStringUtils.randomAlphanumeric(20));
        senderAddress.setPostcode(RandomStringUtils.randomAlphanumeric(7));
        senderAddress.setCountry(RandomStringUtils.randomAlphanumeric(20));
        senderAddress.setStreet(RandomStringUtils.randomAlphanumeric(15));
        senderAddress.setStreetNumber("23b");

        return senderAddress;
    }

    @After
    public void tearDown() throws Exception {

        bookingRepository.deleteAll();

    }

}