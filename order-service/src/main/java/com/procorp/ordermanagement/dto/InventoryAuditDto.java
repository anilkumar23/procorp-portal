package com.procorp.ordermanagement.dto;

public class InventoryAuditDto {

    private Long orderID;

    private Long wareHouseID;

    private Long productID;

    private Long Quantity;

    private String wareHouseLocation;

    private Long ManufacturerID;

    private String deliveryPartner;

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public Long getWareHouseID() {
        return wareHouseID;
    }

    public void setWareHouseID(Long wareHouseID) {
        this.wareHouseID = wareHouseID;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public Long getQuantity() {
        return Quantity;
    }

    public void setQuantity(Long quantity) {
        Quantity = quantity;
    }

    public String getWareHouseLocation() {
        return wareHouseLocation;
    }

    public void setWareHouseLocation(String wareHouseLocation) {
        this.wareHouseLocation = wareHouseLocation;
    }

    public Long getManufacturerID() {
        return ManufacturerID;
    }

    public void setManufacturerID(Long manufacturerID) {
        ManufacturerID = manufacturerID;
    }

    public String getDeliveryPartner() {
        return deliveryPartner;
    }

    public void setDeliveryPartner(String deliveryPartner) {
        this.deliveryPartner = deliveryPartner;
    }
}
