package com.procorp.ordermanagement.dto;

import jakarta.persistence.Column;

public class MonthlySheetReportDto {

    private Long warehouseId;

    private String warehouseLocation;

    private String deliveryFranchise;

    private Long orderId;

    private Long monthYearDate;

    private String orderStatus;

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getMonthYearDate() {
        return monthYearDate;
    }

    public void setMonthYearDate(Long monthYearDate) {
        this.monthYearDate = monthYearDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
