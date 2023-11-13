package com.procorp.ordermanagement.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory_audit")
public class Inventory_Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderID;

    private Long wareHouseID;

    private Long productID;

    private Long Quantity;

    private String wareHouseLocation;

    private Long ManufacturerID;

    private String deliveryPartner;

    @Transient
    private String wareHouseName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getWareHouseName() {
        return wareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
    }
}
