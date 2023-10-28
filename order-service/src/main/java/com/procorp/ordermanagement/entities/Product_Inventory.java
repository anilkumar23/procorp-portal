package com.procorp.ordermanagement.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "product_inventory")
public class Product_Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "productId")
    private Long productId;

    @Column(name = "warehouseId")
    private Long warehouseId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "intialStock")
    private Long intialStock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
