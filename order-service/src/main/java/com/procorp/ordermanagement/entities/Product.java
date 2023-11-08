package com.procorp.ordermanagement.entities;


import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Product name is required.")
    @Basic(optional = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    private Double price;


    private String pictureUrl;

  //  private String productType;

    private String sex;

    private String uom;

    private Double discount;

    private Double lowStockAlert;
    private String status;

    public Product(Long id, String name, Category category, Double price, String pictureUrl, String sex, String uom, Double discount, Double lowStockAlert, String status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.sex = sex;
        this.uom = uom;
        this.discount = discount;
        this.lowStockAlert = lowStockAlert;
        this.status = status;
    }

    public Product() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getLowStockAlert() {
        return lowStockAlert;
    }

    public void setLowStockAlert(Double lowStockAlert) {
        this.lowStockAlert = lowStockAlert;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", sex='" + sex + '\'' +
                ", uom='" + uom + '\'' +
                ", discount=" + discount +
                ", lowStockAlert=" + lowStockAlert +
                ", status='" + status + '\'' +
                '}';
    }
}
