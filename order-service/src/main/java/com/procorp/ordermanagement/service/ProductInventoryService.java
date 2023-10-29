package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.dto.CategoryDto;
import com.procorp.ordermanagement.dto.Product_InventoryDto;
import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.entities.Product_Inventory;

import java.util.Optional;

public interface ProductInventoryService {


    Product_Inventory create(Product_InventoryDto product_InventoryDto);

    Optional<Product_Inventory> update(Long id, Product_InventoryDto product_InventoryDto);

    Optional<Product_Inventory> getProductInventoryById(Long id);

    Iterable<Product_Inventory> getAllProductInventoryDetails();
    void deleteProductInventoryById(Long id);
}