package com.procorp.ordermanagement.service;


import com.procorp.ordermanagement.entities.ManufacturerCategory;
import com.procorp.ordermanagement.entities.OrderProduct;
import com.procorp.ordermanagement.repositories.ManufacturerCategoryRepository;
import com.procorp.ordermanagement.repositories.OrderProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ManufacturerCategoryServiceImpl implements ManufacturerCategoryService {

    private ManufacturerCategoryRepository manufacturerCategoryRepository;

    public ManufacturerCategoryServiceImpl(ManufacturerCategoryRepository manufacturerCategoryRepository) {
        this.manufacturerCategoryRepository = manufacturerCategoryRepository;
    }

    @Override
    public ManufacturerCategory create(ManufacturerCategory manufacturerCategory) {
        return this.manufacturerCategoryRepository.save(manufacturerCategory);
    }
}