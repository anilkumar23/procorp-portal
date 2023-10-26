package com.procorp.ordermanagement.controller;

import com.procorp.ordermanagement.dto.GlobalResponseDTO;
import com.procorp.ordermanagement.dto.ProductDto;
import com.procorp.ordermanagement.entities.Product;
import com.procorp.ordermanagement.service.ProductService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = { "", "/" })
    public @NotNull ResponseEntity<?> getProducts() {

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got all the Products")
                        .responseObj(productService.getAllProducts())
                        .build());
       // return productService.getAllProducts();
    }

    @GetMapping(value = { "/{productId}" })
    public @NotNull ResponseEntity<?> getProductById(@PathVariable(name = "productId")Long productId) {
        Product product =  productService.getProduct(productId);
        if(product!=null){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the Product by ID")
                            .responseObj(product)
                            .build());
        }else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("Product was not found with give ID")
                            .responseObj("Product was not found with give ID")
                            .build());
        }
       // return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping(value = { "","/" })
    public @NotNull ResponseEntity<?> saveProduct(@RequestBody ProductDto dto) {

        Product p = new Product();
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        p.setPictureUrl(dto.getPictureUrl());
        p.setSex(dto.getSex());
        p.setProductType(dto.getProductType());
        p.setStatus(dto.getStatus());
        p.setQuantity(dto.getQuantity());

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED.name())
                        .msg("Product created successfully")
                        .responseObj(productService.save(p))
                        .build());
       // return new ResponseEntity<>(productService.save(p), HttpStatus.CREATED);
    }

    @PutMapping(value = { "/{productId}" })
    public @NotNull ResponseEntity<?> updateProduct(@PathVariable(name = "productId")Long productId,
                                                   @RequestBody ProductDto dto) {
        Product updatedProduct = productService.updateProduct(productId,dto);
        if(updatedProduct!= null){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Updated Product successfully")
                            .responseObj(updatedProduct)
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("Product was not found")
                            .responseObj("Product was not found")
                            .build());
        }
        //return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
    @PutMapping(value = { "/status/{productId}" })
    public @NotNull ResponseEntity<?> updateProduct(@PathVariable(name = "productId")Long productId) {
        Product updatedProduct = productService.updateProductStatus(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Product Status updated Successfully")
                        .responseObj(updatedProduct)
                        .build());
    }


    @DeleteMapping(value = { "/{productId}" })
    public @NotNull ResponseEntity<?> deleteProduct(@PathVariable(name = "productId")Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .status(HttpStatus.NO_CONTENT.name())
                        .msg("Product Deleted Successfully")
                        .responseObj("Product Deleted Successfully")
                        .build());
        //return new ResponseEntity<>("Product Deleted Successfully", HttpStatus.NO_CONTENT);
    }
}
