package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.dto.CategoryDto;
import com.procorp.ordermanagement.entities.Category;

import java.util.Optional;

public interface CategoryService {

    Category create(CategoryDto categorydto);

    Optional<Category> update(Long id,CategoryDto categorydto);

    Optional<Category> getCategoryById(Long id);

    Iterable<Category> getAllCategoryDetails();
    void deleteCategoryById(Long id);

}
