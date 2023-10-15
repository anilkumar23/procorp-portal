package com.procorp.ordermanagement.repositories;

import com.procorp.ordermanagement.entities.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
