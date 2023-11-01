package com.procorp.ordermanagement.controller;



import com.procorp.ordermanagement.dto.GlobalResponseDTO;
import com.procorp.ordermanagement.dto.OrderProductDto;
import com.procorp.ordermanagement.dto.UpdateOrderForm;
import com.procorp.ordermanagement.entities.Order;
import com.procorp.ordermanagement.entities.OrderProduct;
import com.procorp.ordermanagement.entities.OrderStatus;
import com.procorp.ordermanagement.entities.ShoppingCart;
import com.procorp.ordermanagement.exception.ResourceNotFoundException;
import com.procorp.ordermanagement.service.OrderProductService;
import com.procorp.ordermanagement.service.OrderService;
import com.procorp.ordermanagement.service.ProductService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders-service")
public class OrderController {
    ProductService productService;
    OrderService orderService;
    OrderProductService orderProductService;

    public OrderController(ProductService productService, OrderService orderService, OrderProductService orderProductService) {
        this.productService = productService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getOrderList() {

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the order list")
                        .responseObj(this.orderService.getAllOrders())
                        .build());
        //return this.orderService.getAllOrders();
    }

    @GetMapping(path = "/{orderID}")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getOrderById(@PathVariable(name = "orderID") Long orderID) {
        Optional<Order> order= this.orderService.getOrderById(orderID);
        if(order.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the order by orderID")
                            .responseObj(order.get())
                            .build());
            //return new ResponseEntity<>(order.get(), HttpStatus.OK);
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("order was not found")
                            .responseObj("order was not found")
                            .build());
         //   return new ResponseEntity<>("order not found ", HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping(path = "/order/{userID}")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getAllCartDetailsByUserId(@PathVariable(name = "userID") String userID) {
        List<Order> orders= this.orderService.getAllOrderDetailsByUSerId(userID);
        if(orders != null && !orders.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the Order List By userID")
                            .responseObj(orders)
                            .build());
            //return new ResponseEntity<>(cart, HttpStatus.OK);
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("Cart Details was not found")
                            .responseObj("Cart Details was not found")
                            .build());
            // return new ResponseEntity<>("Cart details was not found ", HttpStatus.NO_CONTENT);
        }

    }

    @PutMapping(path = "/update/{orderID}")
    @ResponseStatus(HttpStatus.OK)
   public @NotNull ResponseEntity<?> updateOrderById(@PathVariable(name = "orderID") Long orderID,
                                                     @RequestBody UpdateOrderForm updatedForm) {
               Optional<Order> updatedOrder= this.orderService.updateOrderById(orderID, updatedForm);
                if(updatedOrder.isPresent()){
                    return ResponseEntity.status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(GlobalResponseDTO.builder()
                                    .statusCode(HttpStatus.OK.value())
                                    .status(HttpStatus.OK.name())
                                    .msg("Updated order successfully")
                                    .responseObj(updatedOrder.get())
                                    .build());
                      //  return new ResponseEntity<>(updatedOrder.get(), HttpStatus.OK);
                    }else{
                    return ResponseEntity.status(HttpStatus.OK)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(GlobalResponseDTO.builder()
                                    .statusCode(HttpStatus.NO_CONTENT.value())
                                    .status(HttpStatus.NO_CONTENT.name())
                                    .msg("order was not found")
                                    .responseObj("order was not found")
                                    .build());
                      // return new ResponseEntity<>("order not found ", HttpStatus.NO_CONTENT);
                   }

    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrderForm form) {
        List<OrderProductDto> formDtos = form.getProductOrders();
        validateProductsExistence(formDtos);
        Order order = new Order();
        order.setStatus(OrderStatus.CHECKOUT.name());
        order.setUserId(String.valueOf(form.getUserId()));
        order.setPaymentMode("Not-Yet-Selected");
        order.setAddressId(null);
        order = this.orderService.create(order);

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProductDto dto : formDtos) {
            orderProducts.add(orderProductService.create(new OrderProduct(order, productService.getProduct(dto
                    .getProduct()
                    .getId()), dto.getQuantity())));
        }

        order.setOrderProducts(orderProducts);

        this.orderService.update(order);

        String uri = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/orders/{id}")
                .buildAndExpand(order.getId())
                .toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED.name())
                        .msg("Order created successfully")
                        .responseObj(order)
                        .build());
      //  return new ResponseEntity<>(order, headers, HttpStatus.CREATED);
    }

    private void validateProductsExistence(List<OrderProductDto> orderProducts) {
        List<OrderProductDto> list = orderProducts
                .stream()
                .filter(op -> Objects.isNull(productService.getProduct(op
                        .getProduct()
                        .getId())))
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(list)) {
            new ResourceNotFoundException("Product not found");
        }
    }

    public static class OrderForm {

        private Long userId;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        private List<OrderProductDto> productOrders;

        public List<OrderProductDto> getProductOrders() {
            return productOrders;
        }

        public void setProductOrders(List<OrderProductDto> productOrders) {
            this.productOrders = productOrders;
        }
    }
}
