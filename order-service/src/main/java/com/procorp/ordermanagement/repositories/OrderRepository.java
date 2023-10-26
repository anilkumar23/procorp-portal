package com.procorp.ordermanagement.repositories;

import com.procorp.ordermanagement.entities.Order;
import com.procorp.ordermanagement.entities.ShoppingCart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query(value = "select * from orders where user_id = :userId", nativeQuery = true)
    List<Order> findAllTheOrderDetailsByUserId(@Param("userId") String userId);
}
