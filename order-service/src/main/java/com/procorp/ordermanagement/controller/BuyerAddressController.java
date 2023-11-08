package com.procorp.ordermanagement.controller;

import com.procorp.ordermanagement.dto.Buyer_AddressDto;
import com.procorp.ordermanagement.dto.CategoryDto;
import com.procorp.ordermanagement.dto.GlobalResponseDTO;
import com.procorp.ordermanagement.entities.Buyer_Address;
import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.service.BuyerAddressService;
import com.procorp.ordermanagement.service.CategoryService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/buyer-address-service")
public class BuyerAddressController {

    private BuyerAddressService buyerAddressService;

    public BuyerAddressController(BuyerAddressService buyerAddressService){
        this.buyerAddressService=buyerAddressService;
    }


    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createBuyerAddress(@RequestBody Buyer_AddressDto dto) {
        if(dto==null){
              return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("BuyerAddress input cannot be null")
                            .responseObj("BuyerAddress input cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getAddress()==null || dto.getAddress().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("BuyerAddress cannot be null")
                            .responseObj("BuyerAddress  cannot be null")
                            .build());
        }

        if(dto!=null && (dto.getPincode()==null || dto.getPincode().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("BuyerAddress pincode cannot be null")
                            .responseObj("BuyerAddress pincode cannot be null")
                            .build());
        }

        if(dto!=null && (dto.getUser_id()==null || dto.getUser_id().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("BuyerAddress userID cannot be null")
                            .responseObj("BuyerAddress userID cannot be null")
                            .build());
        }
       Buyer_Address savedEntity = buyerAddressService.create(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED.name())
                        .msg("Buyer_Address created successfully")
                        .responseObj(savedEntity)
                        .build());

    }

    @PutMapping(path = "/{buyerAddressID}")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> updateBuyerAddressById(@PathVariable(name = "buyerAddressID") Long buyerAddressID,
                                                      @RequestBody Buyer_AddressDto dto) {
        if(dto==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("buyerAddress input cannot be null")
                            .responseObj("buyerAddress input cannot be null")
                            .build());
        }
        if(buyerAddressID==0L){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("buyerAddress ID cannot be 0")
                            .responseObj("buyerAddress ID cannot be 0")
                            .build());
        }
        if(dto!=null && (dto.getAddress()==null || dto.getAddress().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("BuyerAddress cannot be null")
                            .responseObj("BuyerAddress  cannot be null")
                            .build());
        }

        if(dto!=null && (dto.getPincode()==null || dto.getPincode().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("BuyerAddress pincode cannot be null")
                            .responseObj("BuyerAddress pincode cannot be null")
                            .build());
        }

        if(dto!=null && (dto.getUser_id()==null || dto.getUser_id().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("BuyerAddress userID cannot be null")
                            .responseObj("BuyerAddress userID cannot be null")
                            .build());
        }
        Optional<Buyer_Address> updatedBuyerAddress= this.buyerAddressService.update(buyerAddressID, dto);
        if(updatedBuyerAddress.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("BuyerAddress updated successfully")
                            .responseObj(updatedBuyerAddress.get())
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("BuyerAddress was not found")
                            .responseObj("BuyerAddress was not found")
                            .build());
        }

    }

    @GetMapping(path = "/{buyerAddressID}")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getCategoryById(@PathVariable(name = "buyerAddressID") Long buyerAddressID) {
        Optional<Buyer_Address> buyerAddress= this.buyerAddressService.getBuyerAddressById(buyerAddressID);
        if(buyerAddress.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the BuyerAddress by ID")
                            .responseObj(buyerAddress.get())
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("BuyerAddress was not found")
                            .responseObj("BuyerAddress was not found")
                            .build());
        }

    }

    @GetMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getBuyerAddressList() {

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the BuyerAddress list")
                        .responseObj(this.buyerAddressService.getAllBuyerAddressDetails())
                        .build());
    }

    @DeleteMapping(value = { "/{buyerAddressID}" })
    public @NotNull ResponseEntity<?> deleteProduct(@PathVariable(name = "buyerAddressID")Long buyerAddressID) {
        this.buyerAddressService.deleteBuyerAddressById(buyerAddressID);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .status(HttpStatus.NO_CONTENT.name())
                        .msg("buyerAddress Deleted Successfully")
                        .responseObj("buyerAddress Deleted Successfully")
                        .build());
    }
}
