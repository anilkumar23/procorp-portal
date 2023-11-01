package com.procorp.ordermanagement.repositories;

import com.procorp.ordermanagement.entities.Warehouse;
import org.springframework.data.repository.CrudRepository;

public interface WarehouseRepository extends CrudRepository<Warehouse,Long> {
}
