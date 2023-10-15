package com.procorp.ordermanagement.repositories;

import com.procorp.ordermanagement.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
