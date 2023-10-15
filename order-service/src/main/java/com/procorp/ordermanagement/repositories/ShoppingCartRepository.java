package com.procorp.ordermanagement.repositories;


import com.procorp.ordermanagement.entities.ShoppingCart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

    @Query(value = "select * from shopping_cart where USER_ID = :userId", nativeQuery = true)
    List<ShoppingCart> findAllTheCartDetailsByUserId(@Param("userId") String userId);

    @Query(value = "select * from shopping_cart where USER_ID = :userId", nativeQuery = true)
    ShoppingCart findCartDetailsByUserId(@Param("userId") String userId);
}
