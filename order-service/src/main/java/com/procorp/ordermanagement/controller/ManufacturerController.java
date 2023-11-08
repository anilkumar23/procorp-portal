package com.procorp.ordermanagement.controller;

import com.procorp.ordermanagement.dto.GlobalResponseDTO;
import com.procorp.ordermanagement.dto.ManufacturerDto;
import com.procorp.ordermanagement.entities.Manufacturer;
import com.procorp.ordermanagement.service.ManufacturerService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/manufacturer-service")
public class ManufacturerController {

    private ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService){
        this.manufacturerService=manufacturerService;
    }


    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createManufacturer(@RequestBody ManufacturerDto dto) {
        if(dto==null){
              return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Manufacturer input cannot be null")
                            .responseObj("Manufacturer input cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getName()==null || dto.getName().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Manufacturer Name cannot be null")
                            .responseObj("Manufacturer Name cannot be null")
                            .build());
        }

        if(dto!=null && (dto.getAddress()==null || dto.getAddress().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Manufacturer Address cannot be null")
                            .responseObj("Manufacturer Address cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getCategoryIds()==null || dto.getCategoryIds().isEmpty())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("CategoryIds be null")
                            .responseObj("CategoryIds be null")
                            .build());
        }
       Manufacturer savedEntity = manufacturerService.create(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED.name())
                        .msg("Manufacturer created successfully")
                        .responseObj(savedEntity)
                        .build());

    }

    @PutMapping(path = "/{manufactureID}")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> updateManufactureById(@PathVariable(name = "manufactureID") Long manufactureID,
                                                      @RequestBody ManufacturerDto dto) {
        if(dto==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("manufacture input cannot be null")
                            .responseObj("manufacture input cannot be null")
                            .build());
        }
        if(manufactureID==0L){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("manufacture ID cannot be 0")
                            .responseObj("manufacture ID cannot be 0")
                            .build());
        }
        if(dto!=null && (dto.getName()==null || dto.getName().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Manufacturer Name cannot be null")
                            .responseObj("Manufacturer Name cannot be null")
                            .build());
        }

        if(dto!=null && (dto.getAddress()==null || dto.getAddress().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Manufacturer Address cannot be null")
                            .responseObj("Manufacturer Address cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getCategoryIds()==null || dto.getCategoryIds().isEmpty())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("CategoryIds be null")
                            .responseObj("CategoryIds be null")
                            .build());
        }
        Optional<Manufacturer> updatedManufacturer= this.manufacturerService.update(manufactureID, dto);
        if(updatedManufacturer.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Manufacturer updated successfully")
                            .responseObj(updatedManufacturer.get())
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("Manufacturer was not found")
                            .responseObj("Manufacturer was not found")
                            .build());
        }

    }

    @GetMapping(path = "/{manufactureID}")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getManufacturerById(@PathVariable(name = "manufactureID") Long manufactureID) {
        Optional<Manufacturer> manufacturer= this.manufacturerService.getManufacturerById(manufactureID);
        if(manufacturer.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the manufacturer by orderID")
                            .responseObj(manufacturer.get())
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("manufacturer was not found")
                            .responseObj("manufacturer was not found")
                            .build());
        }

    }

    @GetMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getManufacturerList() {

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the manufacturer list")
                        .responseObj(this.manufacturerService.getAllManufacturerDetails())
                        .build());
    }

    @DeleteMapping(value = { "/{manufactureID}" })
    public @NotNull ResponseEntity<?> deleteProduct(@PathVariable(name = "manufactureID")Long manufactureID) {
        this.manufacturerService.deleteManufacturerById(manufactureID);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .status(HttpStatus.NO_CONTENT.name())
                        .msg("manufacturer Deleted Successfully")
                        .responseObj("manufacturer Deleted Successfully")
                        .build());
    }
}
