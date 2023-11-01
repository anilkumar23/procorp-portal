package com.procorp.ordermanagement.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy") private LocalDate dateCreated;

    private String userId;

    private String status;

    @OneToMany(mappedBy = "pk.cart")
    @Valid
    private List<CartProduct> cartProducts = new ArrayList<>();

    @Transient
    public Double getTotalOrderPrice() {
        double sum = 0D;
        List<CartProduct> orderProducts = getCartProducts();
        for (CartProduct op : orderProducts) {
            sum += op.getTotalPrice();
        }

        return sum;
    }

   /* @OneToMany
    @JoinTable(name = "shopping_cart_product_mapping",
            joinColumns = {@JoinColumn(name = "cart_fk")},
            inverseJoinColumns = {@JoinColumn(name = "product_fk")}
    )
    public List<Product> products;*/

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
