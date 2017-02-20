package com.codecentric.de.resilient.booking.repository;

import org.springframework.data.repository.CrudRepository;
import com.codecentric.de.resilient.booking.entity.Booking;

/**
 * @author Benjamin Wilms
 */
public interface BookingRepository extends CrudRepository<Booking, Long> {
}
