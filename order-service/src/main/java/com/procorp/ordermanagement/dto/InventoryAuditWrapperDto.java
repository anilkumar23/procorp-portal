package com.procorp.ordermanagement.dto;

import java.util.List;

public class InventoryAuditWrapperDto {

    List<InventoryAuditDto> inventoryAuditDetails;

    public List<InventoryAuditDto> getInventoryAuditDetails() {
        return inventoryAuditDetails;
    }

    public void setInventoryAuditDetails(List<InventoryAuditDto> inventoryAuditDetails) {
        this.inventoryAuditDetails = inventoryAuditDetails;
    }
}
