package com.procorp.ordermanagement.service;


import com.procorp.ordermanagement.entities.ShoppingCart;
import com.procorp.ordermanagement.repositories.ShoppingCartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public Iterable<ShoppingCart> getAllCartDetails() {
        return this.shoppingCartRepository.findAll();
    }

    @Override
    public ShoppingCart create(ShoppingCart cart) {
        cart.setDateCreated(LocalDate.now());

        return this.shoppingCartRepository.save(cart);
    }

    @Override
    public void update(ShoppingCart cart) {
        this.shoppingCartRepository.save(cart);
    }

    @Override
    public Optional<ShoppingCart> getOrderById(Long id){
        return Optional.of(this.shoppingCartRepository.findById(id).get());
    }

    @Override
    public Optional<ShoppingCart> getCartDetailsByUSerId(String userID){
        ShoppingCart cart= this.shoppingCartRepository.findCartDetailsByUserId(userID);
        if(cart != null && cart.getId()!=0L){
            return  Optional.of(cart);
        }else{
            return Optional.empty();
        }

    }

    @Override
    public List<ShoppingCart> getAllCartDetailsByUSerId(String userID){
        return  this.shoppingCartRepository.findAllTheCartDetailsByUserId(userID);
    }
}
