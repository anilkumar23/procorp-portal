package com.procorp.ordermanagement.dto;

public class CategoryDto {

    private String categoryType;

    private String description;

    private String productCategoryTag;

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
