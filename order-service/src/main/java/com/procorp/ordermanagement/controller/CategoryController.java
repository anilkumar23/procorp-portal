package com.procorp.ordermanagement.controller;

import com.procorp.ordermanagement.dto.CategoryDto;
import com.procorp.ordermanagement.dto.GlobalResponseDTO;
import com.procorp.ordermanagement.dto.UpdateOrderForm;
import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.entities.Order;
import com.procorp.ordermanagement.service.CategoryService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
    }


    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto dto) {
        if(dto==null){
              return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Category input cannot be null")
                            .responseObj("Category input cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getCategoryType()==null || dto.getCategoryType().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Category Type cannot be null")
                            .responseObj("Category Type cannot be null")
                            .build());
        }
       Category savedEntity = categoryService.create(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED.name())
                        .msg("Category created successfully")
                        .responseObj(savedEntity)
                        .build());

    }

    @PutMapping(path = "/{categoryID}")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> updateCategoryById(@PathVariable(name = "categoryID") Long categoryID,
                                                      @RequestBody CategoryDto dto) {
        if(dto==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Category input cannot be null")
                            .responseObj("Category input cannot be null")
                            .build());
        }
        if(categoryID==0L){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Category ID cannot be 0")
                            .responseObj("Category ID cannot be 0")
                            .build());
        }
        if(dto!=null && (dto.getCategoryType()==null || dto.getCategoryType().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Category Type cannot be null")
                            .responseObj("Category Type cannot be null")
                            .build());
        }
        Optional<Category> updatedCategory= this.categoryService.update(categoryID, dto);
        if(updatedCategory.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("category updated successfully")
                            .responseObj(updatedCategory.get())
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("category was not found")
                            .responseObj("category was not found")
                            .build());
        }

    }

    @GetMapping(path = "/{categoryID}")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getCategoryById(@PathVariable(name = "categoryID") Long categoryID) {
        Optional<Category> category= this.categoryService.getCategoryById(categoryID);
        if(category.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the category by orderID")
                            .responseObj(category.get())
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("category was not found")
                            .responseObj("category was not found")
                            .build());
        }

    }

    @GetMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getCategoryList() {

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the category list")
                        .responseObj(this.categoryService.getAllCategoryDetails())
                        .build());
    }

    @DeleteMapping(value = { "/{categoryID}" })
    public @NotNull ResponseEntity<?> deleteProduct(@PathVariable(name = "categoryID")Long categoryID) {
        this.categoryService.deleteCategoryById(categoryID);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .status(HttpStatus.NO_CONTENT.name())
                        .msg("category Deleted Successfully")
                        .responseObj("category Deleted Successfully")
                        .build());
    }
}
