package com.procorp.ordermanagement.controller;

import com.procorp.ordermanagement.dto.ProductDto;
import com.procorp.ordermanagement.entities.Product;
import com.procorp.ordermanagement.service.ProductService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
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
    public @NotNull Iterable<Product> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(value = { "/{productId}" })
    public @NotNull ResponseEntity<?> getProductById(@PathVariable(name = "productId")Long productId) {
        Product product =  productService.getProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping(value = { "","/" })
    public @NotNull ResponseEntity<?> saveProduct(@RequestBody ProductDto dto) {

        Product p = new Product();
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        p.setPictureUrl(dto.getPictureUrl());
        p.setSex(dto.getSex());
        p.setDressType(dto.getDressType());
        p.setQuantity(dto.getQuantity());
        return new ResponseEntity<>(productService.save(p), HttpStatus.CREATED);
    }

    @PutMapping(value = { "/{productId}" })
    public @NotNull ResponseEntity<?> updateProduct(@PathVariable(name = "productId")Long productId,
                                                   @RequestBody ProductDto dto) {
        Product updatedProduct = productService.updateProduct(productId,dto);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping(value = { "/{productId}" })
    public @NotNull ResponseEntity<?> deleteProduct(@PathVariable(name = "productId")Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product Deleted Successfully", HttpStatus.NO_CONTENT);
    }
}
