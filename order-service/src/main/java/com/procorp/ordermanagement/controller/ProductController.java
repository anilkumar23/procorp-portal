package com.procorp.ordermanagement.controller;

import com.procorp.ordermanagement.dto.GlobalResponseDTO;
import com.procorp.ordermanagement.dto.OrderProductDto;
import com.procorp.ordermanagement.dto.ProductDto;
import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.entities.Product;
import com.procorp.ordermanagement.exception.ResourceNotFoundException;
import com.procorp.ordermanagement.service.CategoryService;
import com.procorp.ordermanagement.service.ProductService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products-service")
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;

    public ProductController(ProductService productService,CategoryService categoryService) {

        this.productService = productService;
        this.categoryService=categoryService;
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

    @GetMapping(value = { "/searchKey" })
    public @NotNull ResponseEntity<?> getAllTheProductDetailsBySearchKey(
            @RequestParam("keyword") String keyword) {

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got all the Products by search key " + keyword)
                        .responseObj(productService.findAllTheProductDetailsBySearchKey(keyword))
                        .build());
    }

    @GetMapping(value = { "/category" })
    public @NotNull ResponseEntity<?> getProductsByCategoryWise() {

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got all the Products by category")
                        .responseObj(productService.getAllProductsGroupByCategory())
                        .build());
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
            return ResponseEntity.status(HttpStatus.OK)
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

        if(dto==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Product input cannot be null")
                            .responseObj("Product input cannot be null")
                            .build());
        }

        if(dto!=null && (dto.getName()==null || dto.getName().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Product Name cannot be null")
                            .responseObj("Product Name cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getPrice()==null || dto.getPrice()==0L)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Price cannot be null")
                            .responseObj("Price Name cannot be null")
                            .build());
        }

        if(dto!=null && (dto.getCategoryTypeId()==null || dto.getCategoryTypeId()==0L)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("CategoryTypeId cannot be null")
                            .responseObj("CategoryTypeId cannot be null")
                            .build());
        }

        if(dto!=null && (dto.getUom()==null || dto.getUom().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Product UOM cannot be null")
                            .responseObj("Product UOM cannot be null")
                            .build());
        }


        Category category=validateCategoryExistence(dto);
        Product p = new Product();
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        p.setPictureUrl(dto.getPictureUrl());
        p.setSex(dto.getSex());
        p.setUom(dto.getUom());
        p.setCategory(category);
       /* Optional<Category> category= this.categoryService.getCategoryById(dto.getCategoryTypeId());
        if(category.isPresent()){
            p.setCategory(category.get());
        }else{
            throw new ResourceNotFoundException("Category not found for given ID: "+dto.getCategoryTypeId());
        }*/
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

    private Category validateCategoryExistence(ProductDto dto) {
        Optional<Category> category= this.categoryService.getCategoryById(dto.getCategoryTypeId());

        if (!category.isPresent()) {
            new ResourceNotFoundException("Category not found");
        }
        return category.get();
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
            return ResponseEntity.status(HttpStatus.OK)
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
