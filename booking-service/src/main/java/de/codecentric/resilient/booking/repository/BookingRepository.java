package de.codecentric.resilient.booking.repository;

import de.codecentric.resilient.booking.entity.Booking;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Benjamin Wilms
 */
public interface BookingRepository extends CrudRepository<Booking, Long> {
}
