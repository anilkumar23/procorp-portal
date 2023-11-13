package com.procorp.ordermanagement.controller;

import com.procorp.ordermanagement.dto.*;
import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.entities.Product;
import com.procorp.ordermanagement.entities.Product_Inventory;
import com.procorp.ordermanagement.entities.Warehouse;
import com.procorp.ordermanagement.exception.ResourceNotFoundException;
import com.procorp.ordermanagement.service.CategoryService;
import com.procorp.ordermanagement.service.ProductInventoryService;
import com.procorp.ordermanagement.service.ProductService;
import com.procorp.ordermanagement.service.WareHouseService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory-service")
public class ProductInventoryController {

    private ProductInventoryService productInventoryService;

    private ProductService productService;
    private WareHouseService wareHouseService;

    public ProductInventoryController(ProductInventoryService productInventoryService,
                                      ProductService productService,
                                      WareHouseService wareHouseService){
        this.productInventoryService=productInventoryService;
        this.productService=productService;
        this.wareHouseService=wareHouseService;
    }


    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createProductInventory(@RequestBody Product_InventoryDto dto) {
        if(dto==null){
              return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Product Inventory input cannot be null")
                            .responseObj("Product Inventory input cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getProductId()==null || dto.getProductId()==0L)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Product ID cannot be null")
                            .responseObj("Product ID cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getWarehouseId()==null || dto.getWarehouseId()==0L)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Warehouse ID cannot be null")
                            .responseObj("Warehouse ID cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getQuantity()==null || dto.getQuantity()==0L)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Quantity cannot be null")
                            .responseObj("Quantity cannot be null")
                            .build());
        }

        validateProductIdAndWareHouseIdExistence(dto);
       Product_Inventory savedEntity = productInventoryService.create(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED.name())
                        .msg("Product Inventory created successfully")
                        .responseObj(savedEntity)
                        .build());

    }

    private void validateProductIdAndWareHouseIdExistence(Product_InventoryDto dto) {
        Product productObject= this.productService.getProduct(dto.getProductId());

        if (productObject==null) {
           throw new ResourceNotFoundException("ProductID not found in DB");
        }
       Optional<Warehouse> warehouse= this.wareHouseService.getWarehouseById(dto.getWarehouseId());
       if(!warehouse.isPresent()){
           throw new ResourceNotFoundException("WarehouseID not found in DB");
       }
    }

    @PutMapping(path = "/{productInventoryID}")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> updateProductInventoryById(@PathVariable(name = "productInventoryID") Long productInventoryID,
                                                      @RequestBody Product_InventoryDto dto) {
        if(dto==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Product Inventory input cannot be null")
                            .responseObj("Product Inventory input cannot be null")
                            .build());
        }
        if(productInventoryID==0L){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("ProductInventory ID cannot be 0")
                            .responseObj("ProductInventory ID cannot be 0")
                            .build());
        }
        if(dto!=null && (dto.getProductId()==null || dto.getProductId()==0L)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Product ID cannot be null")
                            .responseObj("Product ID cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getWarehouseId()==null || dto.getWarehouseId()==0L)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Warehouse ID cannot be null")
                            .responseObj("Warehouse ID cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getQuantity()==null || dto.getQuantity()==0L)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Quantity cannot be null")
                            .responseObj("Quantity cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getIntialStock()==null || dto.getIntialStock()==0L)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("IntialStock cannot be null")
                            .responseObj("IntialStock cannot be null")
                            .build());
        }
        validateProductIdAndWareHouseIdExistence(dto);
        Optional<Product_Inventory> updatedCategory= this.productInventoryService.update(productInventoryID, dto);
        if(updatedCategory.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Product Inventory updated successfully")
                            .responseObj(updatedCategory.get())
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("Product Inventory was not found")
                            .responseObj("Product Inventory was not found")
                            .build());
        }

    }

    @GetMapping(path = "/{productInventoryID}")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getProductInventoryById(@PathVariable(name = "productInventoryID") Long productInventoryID) {
        Optional<Product_Inventory> productInventory= this.productInventoryService.getProductInventoryById(productInventoryID);
        if(productInventory.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the Product Inventory by ID")
                            .responseObj(productInventory.get())
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("Product Inventory was not found")
                            .responseObj("Product Inventory was not found")
                            .build());
        }

    }

    @GetMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getProductInventoryList() {

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the Product Inventory list")
                        .responseObj(this.productInventoryService.getAllProductInventoryDetails())
                        .build());
    }

    @DeleteMapping(value = { "/{productInventoryID}" })
    public @NotNull ResponseEntity<?> deleteProductInventory(@PathVariable(name = "productInventoryID")Long productInventoryID) {
        this.productInventoryService.deleteProductInventoryById(productInventoryID);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .status(HttpStatus.NO_CONTENT.name())
                        .msg("product Inventory Deleted Successfully")
                        .responseObj("product Inventory Deleted Successfully")
                        .build());
    }



    @PostMapping(path = "/getProductInventoryDetails")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getProductInventoryDetails(@RequestParam String pincode, @RequestBody ProductIDDto productdto) {

        if(productdto==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Product ID's cannot be null")
                            .responseObj("Product ID's cannot be null")
                            .build());
        }
        if(productdto!=null&& (productdto.getProductIds()==null || productdto.getProductIds().isEmpty())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Product ID's cannot be null")
                            .responseObj("Product ID's cannot be null")
                            .build());
        }
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the Product Inventory details with pincode and productID's")
                            .responseObj(this.productInventoryService.getProductInventoryDetails(pincode,productdto.getProductIds()))
                            .build());


    }

    @GetMapping(path = "/isDeliveryPartnerNameValid")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> isDeliveryPartnerNameValid(@RequestParam(name = "partnerName") String partnerName) {
        Boolean isValid= this.productInventoryService.isDeliveryPartnerNameValid(partnerName);
        if(isValid){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Delivery Partner Name was Valid")
                            .responseObj(isValid)
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Delivery Partner Name was not Valid")
                            .responseObj(isValid)
                            .build());
        }



    }

    @GetMapping(path = "/saveInventoryAuditDetails")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> saveInventoryAuditDetails(
            @RequestBody InventoryAuditWrapperDto dto) {
        if(dto==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("InventoryAuditDetail's cannot be null")
                            .responseObj("InventoryAuditDetail's cannot be null")
                            .build());
        }
        if(dto!=null&& (dto.getInventoryAuditDetails()==null || dto.getInventoryAuditDetails().isEmpty())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("InventoryAuditDetails cannot be null")
                            .responseObj("InventoryAuditDetails cannot be null")
                            .build());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED.name())
                        .msg("Saved Inventory Audit Details successfully")
                        .responseObj(this.productInventoryService.saveInventoryAuditDetails(dto.getInventoryAuditDetails()))
                        .build());


    }

    @GetMapping(path = "/getInventoryAuditDetailsByOrderID")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getInventoryAuditDetailsByOrderID(@RequestParam(name = "orderID") Long orderID) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the inventory audit details by orderID: "+orderID)
                        .responseObj(this.productInventoryService.getInventoryAuditByOrderID(orderID))
                        .build());
    }


}
