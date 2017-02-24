package de.codecentric.resilient.address.repository;

import de.codecentric.resilient.address.entity.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * @author Benjamin Wilms
 */
public interface AddressRepository extends CrudRepository<Address, Long>, QueryByExampleExecutor<Address> {

}
