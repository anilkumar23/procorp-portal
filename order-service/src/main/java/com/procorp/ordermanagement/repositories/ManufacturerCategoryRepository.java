package com.procorp.ordermanagement.repositories;


import com.procorp.ordermanagement.entities.ManufacturerCategory;
import com.procorp.ordermanagement.entities.ManufacturerCategoryPK;
import org.springframework.data.repository.CrudRepository;

public interface ManufacturerCategoryRepository extends CrudRepository<ManufacturerCategory, ManufacturerCategoryPK> {
}
