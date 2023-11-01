package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.dto.CategoryDto;
import com.procorp.ordermanagement.dto.WarehouseDto;
import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.entities.Warehouse;

import java.util.Optional;

public interface WareHouseService {


    Warehouse create(WarehouseDto warehouseDto);

    Optional<Warehouse> update(Long id, WarehouseDto warehouseDto);

    Optional<Warehouse> getWarehouseById(Long id);

    Iterable<Warehouse> getAllWarehouseDetails();
    void deleteWarehouseById(Long id);

    Warehouse updateWarehouseStatus(Long id);
}
