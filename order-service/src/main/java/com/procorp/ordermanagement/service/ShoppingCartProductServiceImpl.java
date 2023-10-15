package com.procorp.ordermanagement.service;



import com.procorp.ordermanagement.entities.CartProduct;
import com.procorp.ordermanagement.entities.CartProductPK;
import com.procorp.ordermanagement.repositories.ShoppingCartProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ShoppingCartProductServiceImpl implements ShoppingCartProductService {

    private ShoppingCartProductRepository shoppingCartProductRepository;

    public ShoppingCartProductServiceImpl(ShoppingCartProductRepository shoppingCartProductRepository) {
        this.shoppingCartProductRepository = shoppingCartProductRepository;
    }

    @Override
    public CartProduct create(CartProduct cartProduct) {
        return this.shoppingCartProductRepository.save(cartProduct);
    }

    @Override
    public void delete(CartProductPK cartProductPK){

       Optional<CartProduct> cartProduct= shoppingCartProductRepository.findById(cartProductPK);
        this.shoppingCartProductRepository.delete(cartProduct.get());
    }
}