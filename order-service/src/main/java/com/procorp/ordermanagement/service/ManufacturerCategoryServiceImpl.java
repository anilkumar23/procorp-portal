package com.procorp.ordermanagement.service;


import com.procorp.ordermanagement.entities.*;
import com.procorp.ordermanagement.repositories.ManufacturerCategoryRepository;
import com.procorp.ordermanagement.repositories.OrderProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Override
    public void delete(ManufacturerCategoryPK manufacturerCategoryPK){

        Optional<ManufacturerCategory> ManufacturerCategory= manufacturerCategoryRepository.findById(manufacturerCategoryPK);
        this.manufacturerCategoryRepository.delete(ManufacturerCategory.get());
    }
}