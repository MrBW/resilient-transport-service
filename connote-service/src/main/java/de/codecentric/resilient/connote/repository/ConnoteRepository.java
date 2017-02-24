package de.codecentric.resilient.connote.repository;

import de.codecentric.resilient.connote.entity.Connote;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Benjamin Wilms
 */
public interface ConnoteRepository extends CrudRepository<Connote, Long> {

    @Override
    Connote save(Connote connote);
}
