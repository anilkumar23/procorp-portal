package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.dto.CategoryDto;
import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.entities.Order;
import com.procorp.ordermanagement.exception.ResourceNotFoundException;
import com.procorp.ordermanagement.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    @Override
    public Category create(CategoryDto categorydto) {
        Category category=new Category();
        category.setCategoryType(categorydto.getCategoryType());
        category.setDescription(categorydto.getDescription());
        return this.categoryRepository.save(category);
    }

    @Override
    public Optional<Category> update(Long id, CategoryDto categorydto) {
        Optional<Category> existingCategory = this.categoryRepository.findById(id);
        if(existingCategory.isPresent()){
            Category exisitng = existingCategory.get();
            exisitng.setCategoryType(categorydto.getCategoryType());
            exisitng.setDescription(categorydto.getDescription());
            return Optional.of(this.categoryRepository.save(exisitng));
        }else{
            throw new ResourceNotFoundException("Category was not found with given ID: "+id);
        }
    }

    @Override
    public Optional<Category> getCategoryById(Long id){
        Optional<Category> category = this.categoryRepository.findById(id);
        if(category.isPresent()){
            return category;
        }else{
            throw new ResourceNotFoundException("Category was not found with given ID: "+id);
        }
    }

    @Override
    public Iterable<Category> getAllCategoryDetails(){
        return  this.categoryRepository.findAll();
    }

    @Override
    public void deleteCategoryById(Long id){
        this.categoryRepository.deleteById(id);
    }

}
