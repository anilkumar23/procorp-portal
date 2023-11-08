package com.procorp.ordermanagement.dto;

import jakarta.persistence.Column;

import java.util.List;

public class ManufacturerDto {

    private String name;
    private String location;
    private String description;
    private List<Long> categoryIds;

    private String address;
    private String secondAddress;
    private String city;
    private String state;
    private String pincode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public ManufacturerDto(){

    }

    public ManufacturerDto(String name, String location, String description, List<Long> categoryIds, String address, String secondAddress, String city, String state, String pincode) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.categoryIds = categoryIds;
        this.address = address;
        this.secondAddress = secondAddress;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSecondAddress() {
        return secondAddress;
    }

    public void setSecondAddress(String secondAddress) {
        this.secondAddress = secondAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
