package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.dto.UpdateOrderForm;
import com.procorp.ordermanagement.entities.Order;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Validated
public interface OrderService {

    @NotNull Iterable<Order> getAllOrders();

    Order create(@NotNull(message = "The order cannot be null.") @Valid Order order);

    void update(@NotNull(message = "The order cannot be null.") @Valid Order order);

    Optional<Order> getOrderById(@NotNull(message = "Order id cannot be null.") @Valid Long id);

    Optional<Order> updateOrderById(@NotNull(message = "Order id cannot be null.") @Valid Long id,
                                    UpdateOrderForm updatedForm);

    List<Order> getAllOrderDetailsByUSerId(String userID);
}
