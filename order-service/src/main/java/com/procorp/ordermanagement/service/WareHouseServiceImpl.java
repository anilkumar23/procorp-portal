package com.procorp.ordermanagement.service;


import com.procorp.ordermanagement.dto.CategoryDto;
import com.procorp.ordermanagement.dto.WarehouseDto;
import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.entities.Product;
import com.procorp.ordermanagement.entities.Warehouse;
import com.procorp.ordermanagement.exception.ResourceNotFoundException;
import com.procorp.ordermanagement.repositories.CategoryRepository;
import com.procorp.ordermanagement.repositories.WarehouseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class WareHouseServiceImpl implements WareHouseService{


    private WarehouseRepository warehouseRepository;

    public WareHouseServiceImpl(WarehouseRepository warehouseRepository){
        this.warehouseRepository=warehouseRepository;
    }

    @Override
    public Warehouse create(WarehouseDto warehouseDto) {
        Warehouse warehouse=new Warehouse();
        warehouse.setName(warehouseDto.getName());
        warehouse.setDescription(warehouseDto.getDescription());
        warehouse.setStatus(warehouseDto.getStatus());
        warehouse.setAddress(warehouseDto.getAddress());
        warehouse.setPincode(warehouseDto.getPincode());
        warehouse.setLocation(warehouseDto.getLocation());
        return this.warehouseRepository.save(warehouse);
    }

    @Override
    public Optional<Warehouse> update(Long id, WarehouseDto warehouseDto) {
        Optional<Warehouse> existingWarehouse = this.warehouseRepository.findById(id);
        if(existingWarehouse.isPresent()){
            Warehouse warehouse=existingWarehouse.get();
            warehouse.setName(warehouseDto.getName());
            warehouse.setDescription(warehouseDto.getDescription());
            warehouse.setStatus(warehouseDto.getStatus());
            warehouse.setAddress(warehouseDto.getAddress());
            warehouse.setPincode(warehouseDto.getPincode());
            warehouse.setLocation(warehouseDto.getLocation());
            return Optional.of(this.warehouseRepository.save(warehouse));
        }else{
            throw new ResourceNotFoundException("WareHouse was not found with given ID: "+id);
        }
    }

    @Override
    public Optional<Warehouse> getWarehouseById(Long id){
        Optional<Warehouse> warehouse = this.warehouseRepository.findById(id);
        if(warehouse.isPresent()){
            return warehouse;
        }else{
            throw new ResourceNotFoundException("Warehouse was not found with given ID: "+id);
        }
    }

    @Override
    public Iterable<Warehouse> getAllWarehouseDetails(){
        return  this.warehouseRepository.findAll();
    }

    @Override
    public void deleteWarehouseById(Long id){
        this.warehouseRepository.deleteById(id);
    }

    @Override
    public Warehouse updateWarehouseStatus(Long id){

        Optional<Warehouse> existingWarehouse= warehouseRepository.findById(id);
        if(existingWarehouse.isPresent()){
            Warehouse warehouse=existingWarehouse.get();
            warehouse.setStatus("InActive");
            return warehouseRepository.save(warehouse);
        }else {
            new ResourceNotFoundException("WareHouse was not found");
        }
        return null;
    }

}
