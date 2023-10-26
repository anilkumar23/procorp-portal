package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.dto.UpdateOrderForm;
import com.procorp.ordermanagement.entities.Order;
import com.procorp.ordermanagement.entities.ShoppingCart;
import com.procorp.ordermanagement.exception.ResourceNotFoundException;
import com.procorp.ordermanagement.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Iterable<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    @Override
    public Order create(Order order) {
        order.setDateCreated(LocalDate.now());

        return this.orderRepository.save(order);
    }

    @Override
    public void update(Order order) {
        this.orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrderById(Long id){
        return Optional.of(this.orderRepository.findById(id).get());
    }


    @Override
    public  Optional<Order> updateOrderById(Long id,UpdateOrderForm updatedForm){
                Order existingOrder = this.orderRepository.findById(id).get();
                if(existingOrder!=null){
                     if(updatedForm!=null && updatedForm.getAddressId()!=null){
                               existingOrder.setAddressId(updatedForm.getAddressId());
                           }
                        if(updatedForm!=null && updatedForm.getPaymentMode()!=null){
                                existingOrder.setPaymentMode(updatedForm.getPaymentMode());
                           }
                       if(updatedForm!=null && updatedForm.getOrderStatus()!=null){
                                existingOrder.setStatus(updatedForm.getOrderStatus());
                           }
                        Optional<Order>  updateOrder= Optional.of(orderRepository.save(existingOrder));
                        return updateOrder;
                    }else{
                       throw  new ResourceNotFoundException("Order not found");
                    }
            }

    @Override
    public List<Order> getAllOrderDetailsByUSerId(String userID){
        return  this.orderRepository.findAllTheOrderDetailsByUserId(userID);
    }
}
