package com.procorp.ordermanagement.repositories;

import com.procorp.ordermanagement.entities.Manufacturer;
import org.springframework.data.repository.CrudRepository;

public interface ManufacturerRepository extends CrudRepository<Manufacturer,Long> {
}
