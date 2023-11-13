package com.procorp.ordermanagement.repositories;

import com.procorp.ordermanagement.entities.Inventory_Audit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Inventory_AuditRepository extends CrudRepository<Inventory_Audit, Long> {

    @Query(name = "select * from inventory_audit where orderid=:orderID", nativeQuery = true)
    List<Inventory_Audit> getInventoryAuditByOrderID(@Param("orderID") Long orderID);
}
