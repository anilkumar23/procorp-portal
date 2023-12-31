package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.dto.CategoryDto;
import com.procorp.ordermanagement.dto.InventoryAuditDto;
import com.procorp.ordermanagement.dto.Product_InventoryDto;
import com.procorp.ordermanagement.dto.Product_warehouse_inventory;
import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.entities.Inventory_Audit;
import com.procorp.ordermanagement.entities.Product_Inventory;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductInventoryService {


    Product_Inventory create(Product_InventoryDto product_InventoryDto);

    Optional<Product_Inventory> update(Long id, Product_InventoryDto product_InventoryDto);

    Optional<Product_Inventory> getProductInventoryById(Long id);

    Iterable<Product_Inventory> getAllProductInventoryDetails();
    void deleteProductInventoryById(Long id);

    Map<String, List<Product_warehouse_inventory>> getProductInventoryDetails(String pincode, List<Long> productIds);


    boolean isDeliveryPartnerNameValid(String name);

    String saveInventoryAuditDetails(List<InventoryAuditDto> inventoryAuditDetails);

    List<Inventory_Audit> getInventoryAuditByOrderID(Long orderID);
}
