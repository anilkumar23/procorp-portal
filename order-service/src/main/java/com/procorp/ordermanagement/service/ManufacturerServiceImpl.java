package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.dto.ManufacturerDto;
import com.procorp.ordermanagement.entities.*;
import com.procorp.ordermanagement.exception.ResourceNotFoundException;
import com.procorp.ordermanagement.repositories.CategoryRepository;
import com.procorp.ordermanagement.repositories.ManufacturerCategoryRepository;
import com.procorp.ordermanagement.repositories.ManufacturerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ManufacturerServiceImpl implements ManufacturerService {

    private ManufacturerRepository manufacturerRepository;

    private CategoryRepository categoryRepository;

    private ManufacturerCategoryService manufacturerCategoryService;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository,
                                   CategoryRepository categoryRepository,
                                   ManufacturerCategoryService manufacturerCategoryService){
        this.manufacturerRepository=manufacturerRepository;
        this.categoryRepository=categoryRepository;
        this.manufacturerCategoryService=manufacturerCategoryService;
    }

    @Override
    public Manufacturer create(ManufacturerDto dto) {
        Manufacturer manufacturer=new Manufacturer();
        manufacturer.setName(dto.getName());
        manufacturer.setLocation(dto.getLocation());
        manufacturer.setDescription(dto.getDescription());
        manufacturer.setSecondAddress(dto.getSecondAddress());
        manufacturer.setPincode(dto.getPincode());
        manufacturer.setAddress(dto.getAddress());
        manufacturer.setCity(dto.getCity());
        manufacturer.setState(dto.getState());

        manufacturer = this.manufacturerRepository.save(manufacturer);

        List<ManufacturerCategory> manufacturerCategories=new ArrayList<>();
        if(dto.getCategoryIds()!=null && !dto.getCategoryIds().isEmpty()){
            for (Long categoryID: dto.getCategoryIds() ) {
                Optional<Category> category = this.categoryRepository.findById(categoryID);
                if(category.isPresent()){
                    manufacturerCategories.add(this.manufacturerCategoryService
                            .create(new ManufacturerCategory(manufacturer,category.get())));
                    //new ManufacturerCategory(manufacturer,category.get())
                }else {
                    throw new ResourceNotFoundException("category was not found for give ID: "+categoryID);
                }
            }

        }
        manufacturer.setManufacturerCategories(manufacturerCategories);
        try{
            return this.manufacturerRepository.save(manufacturer);
        }catch (Exception e){
            e.printStackTrace();
        }
       return  null;
    }

    @Override
    public Optional<Manufacturer> update(Long id, ManufacturerDto dto) {
        Optional<Manufacturer> existingManufacturer = this.manufacturerRepository.findById(id);
        if(existingManufacturer.isPresent()){
            Manufacturer existing  = existingManufacturer.get();
            existing.setName(dto.getName());
            existing.setDescription(dto.getDescription());
            existing.setLocation(dto.getLocation());
            existing.setSecondAddress(dto.getSecondAddress());
            existing.setPincode(dto.getPincode());
            existing.setAddress(dto.getAddress());
            existing.setCity(dto.getCity());
            existing.setState(dto.getState());


            List<Long> deleteIds=new ArrayList<>();
            List<Long> newIds=new ArrayList<>();
            List<ManufacturerCategory> existingManufacturerCategories = existing.getManufacturerCategories();

            existingManufacturerCategories.forEach(manufacturer-> {
                if(!dto.getCategoryIds().contains(manufacturer.getCategory().getId())){
                    deleteIds.add(manufacturer.getCategory().getId());
                }
            });
            dto.getCategoryIds().forEach(categoryID->{
              Optional<ManufacturerCategory> manufacturer=  existingManufacturerCategories
                        .stream()
                        .filter(manufacture->manufacture.getCategory().getId().equals(categoryID)).findAny();
              if(!manufacturer.isPresent()){
                  newIds.add(categoryID);
              }
            });

            List<ManufacturerCategory> manufacturerCategories=new ArrayList<>();

            //deleting existing categories
            for (Long categoryID: deleteIds ){
                Optional<Category> category = this.categoryRepository.findById(categoryID);
                if(category.isPresent()){
                    ManufacturerCategoryPK manufacturerCategorypk=new ManufacturerCategoryPK();
                    manufacturerCategorypk.setManufacturer(existing);
                    manufacturerCategorypk.setCategory(category.get());
                    this.manufacturerCategoryService.delete(manufacturerCategorypk);
                }else {
                    throw new ResourceNotFoundException("category was not found for give ID: "+categoryID);
                }
            }

            // Adding new category ID's
            for (Long categoryID: newIds){
                Optional<Category> category = this.categoryRepository.findById(categoryID);
                if(category.isPresent()){
                    manufacturerCategories.add(this.manufacturerCategoryService
                            .create(new ManufacturerCategory(existingManufacturer.get(),category.get())));
                }else {
                    throw new ResourceNotFoundException("category was not found for give ID: "+categoryID);
                }
            }
            existing.setManufacturerCategories(manufacturerCategories);
            return Optional.of(this.manufacturerRepository.save(existing));
        }else{
            throw new ResourceNotFoundException("Manufacturer was not found with given ID: "+id);
        }
    }

    @Override
    public Optional<Manufacturer> getManufacturerById(Long id){
        Optional<Manufacturer> manufacturer = this.manufacturerRepository.findById(id);
        if(manufacturer.isPresent()){
            return manufacturer;
        }else{
            throw new ResourceNotFoundException("Manufacturer was not found with given ID: "+id);
        }
    }

    @Override
    public Iterable<Manufacturer> getAllManufacturerDetails(){
        return  this.manufacturerRepository.findAll();
    }

    @Override
    public void deleteManufacturerById(Long id){
        this.manufacturerRepository.deleteById(id);
    }

}
