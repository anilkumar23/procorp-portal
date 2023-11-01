package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.dto.CategoryDto;
import com.procorp.ordermanagement.dto.Product_InventoryDto;
import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.entities.Product_Inventory;
import com.procorp.ordermanagement.exception.ResourceNotFoundException;
import com.procorp.ordermanagement.repositories.CategoryRepository;
import com.procorp.ordermanagement.repositories.Product_InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProductInventoryServiceImpl implements ProductInventoryService{

    private Product_InventoryRepository product_inventoryRepository;

    public ProductInventoryServiceImpl(Product_InventoryRepository product_inventoryRepository){
        this.product_inventoryRepository=product_inventoryRepository;
    }

    @Override
    public Product_Inventory create(Product_InventoryDto dto) {
        Product_Inventory productInventory=new Product_Inventory();
        productInventory.setProductId(dto.getProductId());
        productInventory.setWarehouseId(dto.getWarehouseId());
        productInventory.setQuantity(dto.getQuantity());
        productInventory.setIntialStock(dto.getIntialStock());
        return this.product_inventoryRepository.save(productInventory);
    }

    @Override
    public Optional<Product_Inventory> update(Long id, Product_InventoryDto dto) {
        Optional<Product_Inventory> existingProductInventory = this.product_inventoryRepository.findById(id);
        if(existingProductInventory.isPresent()){
            Product_Inventory productInventory=existingProductInventory.get();
            productInventory.setProductId(dto.getProductId());
            productInventory.setWarehouseId(dto.getWarehouseId());
            productInventory.setQuantity(dto.getQuantity());
            productInventory.setIntialStock(dto.getIntialStock());
            return Optional.of(this.product_inventoryRepository.save(productInventory));
        }else{
            throw new ResourceNotFoundException("Product Inventory was not found with given ID: "+id);
        }
    }

    @Override
    public Optional<Product_Inventory> getProductInventoryById(Long id){
        Optional<Product_Inventory> product_Inventory = this.product_inventoryRepository.findById(id);
        if(product_Inventory.isPresent()){
            return product_Inventory;
        }else{
            throw new ResourceNotFoundException("productInventory was not found with given ID: "+id);
        }
    }

    @Override
    public Iterable<Product_Inventory> getAllProductInventoryDetails(){
        return  this.product_inventoryRepository.findAll();
    }

    @Override
    public void deleteProductInventoryById(Long id){
        this.product_inventoryRepository.deleteById(id);
    }



}
