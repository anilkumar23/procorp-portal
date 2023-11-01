package com.procorp.ordermanagement.service;


import com.procorp.ordermanagement.entities.CartProduct;
import com.procorp.ordermanagement.entities.CartProductPK;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ShoppingCartProductService {

    CartProduct create(@NotNull(message = "The products for order cannot be null.") @Valid CartProduct cartProduct);

    void delete(CartProductPK cartProductPK);

  }
