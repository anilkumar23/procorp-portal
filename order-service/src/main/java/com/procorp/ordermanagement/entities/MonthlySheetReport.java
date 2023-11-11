package com.procorp.ordermanagement.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "monthlysheetreport",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"monthYear","warehouseId"})
})
public class MonthlySheetReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy") private LocalDate dateCreated;

    @Column(name = "monthYear")
    @JsonFormat(pattern = "MM/yyyy") private LocalDate monthYear;

    @Column(name = "createdDate")
    private Long createdDate;

    @Column(name = "warehouseId")
    private Long warehouseId;

    @Column(name = "warehouseLocation")
    private String warehouseLocation;

    @Column(name = "deliveryFranchise")
    private String deliveryFranchise;

    @Column(name = "noOfOrdersReceived")
    private Long noOfOrdersReceived;

    @Column(name = "noOfOrdersDelivered")
    private Long noOfOrdersDelivered;

    @Column(name = "noOfOrdersReturned")
    private Long noOfOrdersReturned;

    @Column(name = "salesVolumes")
    private Long salesVolumes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(LocalDate monthYear) {
        this.monthYear = monthYear;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }

    public String getDeliveryFranchise() {
        return deliveryFranchise;
    }

    public void setDeliveryFranchise(String deliveryFranchise) {
        this.deliveryFranchise = deliveryFranchise;
    }

    public Long getNoOfOrdersReceived() {
        return noOfOrdersReceived;
    }

    public void setNoOfOrdersReceived(Long noOfOrdersReceived) {
        this.noOfOrdersReceived = noOfOrdersReceived;
    }

    public Long getNoOfOrdersDelivered() {
        return noOfOrdersDelivered;
    }

    public void setNoOfOrdersDelivered(Long noOfOrdersDelivered) {
        this.noOfOrdersDelivered = noOfOrdersDelivered;
    }

    public Long getNoOfOrdersReturned() {
        return noOfOrdersReturned;
    }

    public void setNoOfOrdersReturned(Long noOfOrdersReturned) {
        this.noOfOrdersReturned = noOfOrdersReturned;
    }

    public Long getSalesVolumes() {
        return salesVolumes;
    }

    public void setSalesVolumes(Long salesVolumes) {
        this.salesVolumes = salesVolumes;
    }
}
