package com.procorp.ordermanagement.dto;

import jakarta.persistence.Column;

import java.util.List;

public class ManufacturerDto {

    private String name;
    private String location;
    private String description;
    private List<Long> categoryIds;

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

    public ManufacturerDto(String name, String location, String description, List<Long> categoryIds) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.categoryIds = categoryIds;
    }

    @Override
    public String toString() {
        return "ManufacturerDto{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", categoryIds=" + categoryIds +
                '}';
    }
}
