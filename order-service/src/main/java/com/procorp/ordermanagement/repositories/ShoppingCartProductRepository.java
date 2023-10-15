package com.procorp.ordermanagement.repositories;


import com.procorp.ordermanagement.entities.CartProduct;
import com.procorp.ordermanagement.entities.CartProductPK;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartProductRepository extends CrudRepository<CartProduct, CartProductPK> {
}
