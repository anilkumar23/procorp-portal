package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.entities.ShoppingCart;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Validated
public interface ShoppingCartService {

    @NotNull Iterable<ShoppingCart> getAllCartDetails();

    ShoppingCart create(@NotNull(message = "The shopping cart cannot be null.") @Valid ShoppingCart cart);

    void update(@NotNull(message = "The shopping cart cannot be null.") @Valid ShoppingCart cart);

    Optional<ShoppingCart> getCartDetailsByUSerId(String userID);

    List<ShoppingCart> getAllCartDetailsByUSerId(String userID);

    Optional<ShoppingCart> getOrderById(@NotNull(message = "shopping cart id cannot be null.") @Valid Long id);
}
