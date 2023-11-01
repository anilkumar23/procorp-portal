package com.procorp.ordermanagement.dto;

public class Product_InventoryDto {

    private Long productId;
    private Long warehouseId;
    private Long quantity;
    private Long intialStock;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getIntialStock() {
        return intialStock;
    }

    public void setIntialStock(Long intialStock) {
        this.intialStock = intialStock;
    }
}
