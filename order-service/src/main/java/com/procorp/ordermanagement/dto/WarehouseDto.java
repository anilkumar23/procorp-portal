package com.procorp.ordermanagement.dto;

public class WarehouseDto {

    private String name;
    private String address;
    private String pincode;
    private String location;
    private String status;

    private String secondAddress;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecondAddress() {
        return secondAddress;
    }

    public void setSecondAddress(String secondAddress) {
        this.secondAddress = secondAddress;
    }

    @Override
    public String toString() {
        return "WarehouseDto{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", pincode='" + pincode + '\'' +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", secondAddress='" + secondAddress + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
