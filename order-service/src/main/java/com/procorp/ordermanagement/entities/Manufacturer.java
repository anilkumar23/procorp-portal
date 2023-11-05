package com.procorp.ordermanagement.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "manufacturer")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="manufacturerCategories")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "pk.manufacturer")
    @Valid
    private List<ManufacturerCategory> manufacturerCategories = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<ManufacturerCategory> getManufacturerCategories() {
        return manufacturerCategories;
    }

    public void setManufacturerCategories(List<ManufacturerCategory> manufacturerCategories) {
        this.manufacturerCategories = manufacturerCategories;
    }
}
