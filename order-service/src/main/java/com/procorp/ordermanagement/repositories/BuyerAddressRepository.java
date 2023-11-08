package com.procorp.ordermanagement.repositories;

import com.procorp.ordermanagement.entities.Buyer_Address;
import com.procorp.ordermanagement.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface BuyerAddressRepository extends CrudRepository<Buyer_Address,Long> {
}
