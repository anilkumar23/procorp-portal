package com.procorp.ordermanagement.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "categoryType")
    private String categoryType;

    @Column(name = "productCategoryTag")
    private String productCategoryTag;
    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductCategoryTag() {
        return productCategoryTag;
    }

    public void setProductCategoryTag(String productCategoryTag) {
        this.productCategoryTag = productCategoryTag;
    }
}
