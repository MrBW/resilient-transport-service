package com.codecentric.de.address.repository;

import com.codecentric.de.address.entity.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * @author Benjamin Wilms
 */
public interface AddressRepository extends CrudRepository<Address, Long>, QueryByExampleExecutor<Address> {

}
