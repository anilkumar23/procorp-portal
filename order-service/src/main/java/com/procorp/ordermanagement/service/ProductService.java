package com.procorp.ordermanagement.service;


import com.procorp.ordermanagement.dto.ProductDto;
import com.procorp.ordermanagement.entities.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Validated
public interface ProductService {

    @NotNull Iterable<Product> getAllProducts();

    Product getProduct(@Min(value = 1L, message = "Invalid product ID.") long id);

    Product save(Product product);

    public void deleteProduct(Long id);

    public Product updateProduct(Long id, ProductDto dto);

    Product updateProductStatus(Long id);

    Map<String, List<Product>> getAllProductsGroupByCategory();

    List<Product> findAllTheProductDetailsBySearchKey(String searchKey);

    List<Product> findProductsByCategoryID(Long categoryId);
}
