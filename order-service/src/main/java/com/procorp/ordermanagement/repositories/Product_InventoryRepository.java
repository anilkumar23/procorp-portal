package com.procorp.ordermanagement.repositories;

import com.procorp.ordermanagement.entities.Product_Inventory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface Product_InventoryRepository extends CrudRepository<Product_Inventory,Long> {

    @Query(value = "select * from product_inventory where product_id=:productID and warehouse_id=:warehouseID", nativeQuery = true)
    Product_Inventory getProductInventoryByProductIdAndWareHouseId(@Param("productID") Long productID,
                                                                   @Param("warehouseID") Long warehouseID);
}
