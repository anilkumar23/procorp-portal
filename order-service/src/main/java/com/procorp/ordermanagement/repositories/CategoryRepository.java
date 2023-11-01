package com.procorp.ordermanagement.repositories;

import com.procorp.ordermanagement.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Long> {
}
