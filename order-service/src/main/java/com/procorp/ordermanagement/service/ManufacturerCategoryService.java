package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.entities.ManufacturerCategory;
import com.procorp.ordermanagement.entities.OrderProduct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ManufacturerCategoryService {

    ManufacturerCategory create(@NotNull(message = "The categories for Manufacturer cannot be null.") @Valid ManufacturerCategory manufacturerCategory);
}
