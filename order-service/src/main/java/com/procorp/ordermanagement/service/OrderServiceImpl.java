package com.procorp.ordermanagement.service;

import com.procorp.ordermanagement.dto.UpdateOrderForm;
import com.procorp.ordermanagement.entities.Buyer_Address;
import com.procorp.ordermanagement.entities.Order;
import com.procorp.ordermanagement.entities.ShoppingCart;
import com.procorp.ordermanagement.exception.ResourceNotFoundException;
import com.procorp.ordermanagement.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private BuyerAddressService buyerAddressService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            BuyerAddressService buyerAddressService) {
        this.orderRepository = orderRepository;
        this.buyerAddressService=buyerAddressService;
    }

    @Override
    public Iterable<Order> getAllOrders() {
        Iterable<Order> orders = this.orderRepository.findAll();
        orders.iterator().forEachRemaining(existingOrder -> {
            if(existingOrder.getAddressId()!=null&&!existingOrder.getAddressId().isBlank()){
                Optional<Buyer_Address> address=  buyerAddressService.getBuyerAddressById(Long.valueOf(existingOrder.getAddressId()));
                existingOrder.setBuyerAddress(address.get());
            }
        });
        return orders;
       // return this.orderRepository.findAll();
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
      Optional<Order> existingOrder =  this.orderRepository.findById(id);
      if(existingOrder.isPresent()){
          if(existingOrder.get().getAddressId()!=null&&!existingOrder.get().getAddressId().isBlank()){
              Optional<Buyer_Address> address=  buyerAddressService.getBuyerAddressById(Long.valueOf(existingOrder.get().getAddressId()));
              existingOrder.get().setBuyerAddress(address.get());
          }
          return existingOrder;
      }else{
          throw new ResourceNotFoundException("Order ID was not found");
      }
       // return Optional.of(this.orderRepository.findById(id).get());
    }


    @Override
    public  Optional<Order> updateOrderById(Long id,UpdateOrderForm updatedForm){
                Order existingOrder = this.orderRepository.findById(id).get();
                existingOrder.setModifiedDate(new Date().getTime());
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
