package com.procorp.ordermanagement.controller;


import com.procorp.ordermanagement.dto.CartProductDto;
import com.procorp.ordermanagement.dto.GlobalResponseDTO;
import com.procorp.ordermanagement.entities.CartProduct;
import com.procorp.ordermanagement.entities.CartProductPK;
import com.procorp.ordermanagement.entities.ShoppingCart;
import com.procorp.ordermanagement.exception.ResourceNotFoundException;
import com.procorp.ordermanagement.service.ProductService;
import com.procorp.ordermanagement.service.ShoppingCartProductService;
import com.procorp.ordermanagement.service.ShoppingCartService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    ProductService productService;
    ShoppingCartService shoppingCartService;
    ShoppingCartProductService shoppingCartProductService;

    public ShoppingCartController(ProductService productService, ShoppingCartService shoppingCartService, ShoppingCartProductService shoppingCartProductService) {
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartProductService = shoppingCartProductService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getCartList() {

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the cart list")
                        .responseObj(this.shoppingCartService.getAllCartDetails())
                        .build());
        //return this.shoppingCartService.getAllCartDetails();
    }

    @GetMapping(path = "/{cartID}")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getCartItemById(@PathVariable(name = "cartID") Long cartID) {
        Optional<ShoppingCart> cart= this.shoppingCartService.getOrderById(cartID);
        if(cart.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the Cart Item by ID")
                            .responseObj(cart.get())
                            .build());
           // return new ResponseEntity<>(cart.get(), HttpStatus.OK);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("Cart was not found")
                            .responseObj("Cart was not found")
                            .build());
           // return new ResponseEntity<>("order not found ", HttpStatus.NO_CONTENT);
        }

    }
    @GetMapping(path = "/user/{userID}")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getCartDetailsByUserId(@PathVariable(name = "userID") String userID) {
        Optional<ShoppingCart> cart= this.shoppingCartService.getCartDetailsByUSerId(userID);
        if(cart.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the Cart Item by userID")
                            .responseObj(cart.get())
                            .build());
            //return new ResponseEntity<>(cart.get(), HttpStatus.OK);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("Cart Details was not found")
                            .responseObj("Cart Details was not found")
                            .build());
            //return new ResponseEntity<>("Cart Details was not found ", HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping(path = "/cart/{userID}")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getAllCartDetailsByUserId(@PathVariable(name = "userID") String userID) {
        List<ShoppingCart> cart= this.shoppingCartService.getAllCartDetailsByUSerId(userID);
        if(cart != null && !cart.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the Cart List By userID")
                            .responseObj(cart)
                            .build());
            //return new ResponseEntity<>(cart, HttpStatus.OK);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
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

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CartForm form) {
        List<CartProductDto> formDtos = form.getCartProducts();
        validateProductsExistence(formDtos);
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(form.getUserID());
        cart.setStatus("under_cart");
        cart = this.shoppingCartService.create(cart);

        List<CartProduct> cartProducts = new ArrayList<>();
        for (CartProductDto dto : formDtos) {
            cartProducts.add(shoppingCartProductService.
                    create(new CartProduct(cart, productService.getProduct(dto.getProduct()
                    .getId()), dto.getQuantity())));
        }

        cart.setCartProducts(cartProducts);

        this.shoppingCartService.update(cart);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED.name())
                        .msg("ShoppingCart created successfully")
                        .responseObj(cart)
                        .build());
      //  return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }


    @PutMapping(value = { "/{userID}" })
    public @NotNull ResponseEntity<?> updateCart(@PathVariable(name = "userID")String userId,
                                                 @RequestBody UpdatedCartForm form) {
        List<CartProductDto> formDtos = form.getCartProducts();
        validateProductsExistence(formDtos);

        Optional<ShoppingCart> updatedCart = shoppingCartService.getCartDetailsByUSerId(userId);
        if(updatedCart.isPresent()){
            if(form.getAction().equalsIgnoreCase("Add")){

                List<CartProduct> existingList = updatedCart.get().getCartProducts();
                Map<Long, List<CartProduct>> map =  existingList.stream().
                        collect(Collectors.groupingBy(p-> p.getProduct().getId()));
                for (CartProductDto dto : formDtos) {
                    if(!map.containsKey(dto.getProduct().getId())) {
                        existingList.add(shoppingCartProductService.create(new CartProduct(updatedCart.get(), productService.getProduct(dto
                                .getProduct()
                                .getId()), dto.getQuantity())));
                    }
                }
                updatedCart.get().setCartProducts(existingList);
            }else if(form.getAction().equalsIgnoreCase("Remove")){
                Map<Long, List<CartProductDto>> map =  form.getCartProducts().stream().
                        collect(Collectors.groupingBy(p-> p.getProduct().getId()));

                List<CartProduct> existingList =
                        updatedCart.get().getCartProducts().stream()
                                .filter(ep ->
                                {
                                  if(map.containsKey(ep.getProduct().getId())){
                                      CartProductPK cartProductPK= new CartProductPK();
                                      cartProductPK.setCart(updatedCart.get());
                                      cartProductPK.setProduct(productService.getProduct(ep.getProduct().getId()));
                                      shoppingCartProductService.delete(cartProductPK);
                                      return false;
                                  }else{
                                      return true;
                                  }
                                  // return map.containsKey(ep.getProduct().getId());
                                })
                                /*.peek(ep-> {
                                    CartProductPK cartProductPK= new CartProductPK();
                                    cartProductPK.setCart(updatedCart.get());
                                    cartProductPK.setProduct(productService.getProduct(ep.getProduct().getId()));
                                    shoppingCartProductService.delete(cartProductPK);
                                })*/
                                .collect(Collectors.toList());
                updatedCart.get().setCartProducts(existingList);


            }else if(form.getAction().equalsIgnoreCase("QuantityUpdate")){
                Map<Long,Integer> mapWithQuantity = new HashMap<>();
                 form.getCartProducts().stream().forEach(p->{
                     mapWithQuantity.put(p.getProduct().getId(),p.getQuantity());
                 });

               // List<CartProduct> existingList =
                        updatedCart.get().getCartProducts()
                        .forEach(ep-> {
                           if(mapWithQuantity.containsKey(ep.getProduct().getId())){
                              ep.setQuantity(mapWithQuantity.get(ep.getProduct().getId()));
                            }
                        });

            ///    updatedCart.get().setCartProducts(existingList);

            }

            /*List<CartProduct> cartProducts = new ArrayList<>();

            for (CartProductDto dto : formDtos) {
                cartProducts.add(shoppingCartProductService.create(new CartProduct(updatedCart.get(), productService.getProduct(dto
                        .getProduct()
                        .getId()), dto.getQuantity())));
            }

            updatedCart.get().setCartProducts(cartProducts);
*/
            this.shoppingCartService.update(updatedCart.get());

        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("Cart Details was not found")
                            .responseObj("Cart Details was not found")
                            .build());
           // return new ResponseEntity<>("No Data found", HttpStatus.NO_CONTENT);
        }


        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Updated Cart successfully")
                        .responseObj(updatedCart)
                        .build());
       // return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

    private void validateProductsExistence(List<CartProductDto> cartProducts) {
        List<CartProductDto> list = cartProducts
                .stream()
                .filter(op -> Objects.isNull(productService.getProduct(op
                        .getProduct()
                        .getId())))
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(list)) {
            new ResourceNotFoundException("Product not found");
        }
    }

    public static class CartForm {


        private String userID;

        private List<CartProductDto> cartProducts;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public List<CartProductDto> getCartProducts() {
            return cartProducts;
        }

        public void setCartProducts(List<CartProductDto> cartProducts) {
            this.cartProducts = cartProducts;
        }
    }

    public static class UpdatedCartForm {


        private String userID;
        private String action;

        private List<CartProductDto> cartProducts;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public List<CartProductDto> getCartProducts() {
            return cartProducts;
        }

        public void setCartProducts(List<CartProductDto> cartProducts) {
            this.cartProducts = cartProducts;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
}
