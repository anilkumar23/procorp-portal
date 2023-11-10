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



}
