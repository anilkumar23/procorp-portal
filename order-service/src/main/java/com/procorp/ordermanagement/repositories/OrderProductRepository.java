package com.procorp.ordermanagement.repositories;


import com.procorp.ordermanagement.entities.OrderProduct;
import com.procorp.ordermanagement.entities.OrderProductPK;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepository extends CrudRepository<OrderProduct, OrderProductPK> {
}
