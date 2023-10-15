package com.procorp.ordermanagement.entities;


import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Product name is required.")
    @Basic(optional = false)
    private String name;

    private Double price;

    private Double quantity;

    private String pictureUrl;

    private String dressType;

    private String sex;


    public Product(Long id, String name, Double price, Double quantity, String pictureUrl, String dressType, String sex) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.pictureUrl = pictureUrl;
        this.dressType = dressType;
        this.sex = sex;
    }

    public Product() {
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDressType() {
        return dressType;
    }

    public void setDressType(String dressType) {
        this.dressType = dressType;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", dressType='" + dressType + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
