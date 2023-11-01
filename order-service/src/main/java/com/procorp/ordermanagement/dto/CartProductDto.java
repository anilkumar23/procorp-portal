package com.procorp.ordermanagement.dto;


import com.procorp.ordermanagement.entities.Product;

public class CartProductDto {

    private Product product;
    private Integer quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartProductDto{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
