package com.codecentric.de.resilient.connote.repository;

import org.springframework.data.repository.CrudRepository;
import com.codecentric.de.resilient.connote.entity.Connote;

/**
 * @author Benjamin Wilms
 */
public interface ConnoteRepository extends CrudRepository<Connote, Long> {

    @Override
    Connote save(Connote connote);
}
