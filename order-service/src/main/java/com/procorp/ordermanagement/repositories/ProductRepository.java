package com.procorp.ordermanagement.repositories;

import com.procorp.ordermanagement.entities.Order;
import com.procorp.ordermanagement.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    //@Query("select * from product")

    @Query(value = "SELECT * FROM product  where LOWER(name) like %:searchKey%", nativeQuery = true)
    List<Product> findByNameContaining(@Param("searchKey") String searchKey);
}
