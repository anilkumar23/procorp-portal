package com.procorp.ordermanagement.controller;

import com.procorp.ordermanagement.dto.CategoryDto;
import com.procorp.ordermanagement.dto.GlobalResponseDTO;
import com.procorp.ordermanagement.dto.WarehouseDto;
import com.procorp.ordermanagement.entities.Category;
import com.procorp.ordermanagement.entities.Product;
import com.procorp.ordermanagement.entities.Warehouse;
import com.procorp.ordermanagement.service.CategoryService;
import com.procorp.ordermanagement.service.WareHouseService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/warehouse-service")
public class WarehouseController {

    private WareHouseService wareHouseService;

    public WarehouseController(WareHouseService wareHouseService){

        this.wareHouseService=wareHouseService;
    }


    @PostMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createWareHouse(@RequestBody WarehouseDto dto) {
        if(dto==null){
              return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("Warehouse input cannot be null")
                            .responseObj("Warehouse input cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getName()==null || dto.getName().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("warehouse Name cannot be null")
                            .responseObj("warehouse Name cannot be null")
                            .build());
        }

        if(dto!=null && (dto.getAddress()==null || dto.getAddress().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("warehouse Address cannot be null")
                            .responseObj("warehouse Address cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getPincode()==null || dto.getPincode().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("warehouse pincode cannot be null")
                            .responseObj("warehouse pincode cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getStatus()==null || dto.getStatus().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("warehouse status cannot be null")
                            .responseObj("warehouse status cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getLocation()==null || dto.getLocation().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("warehouse location cannot be null")
                            .responseObj("warehouse location cannot be null")
                            .build());
        }
       Warehouse savedEntity = wareHouseService.create(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .status(HttpStatus.CREATED.name())
                        .msg("Warehouse created successfully")
                        .responseObj(savedEntity)
                        .build());

    }

    @PutMapping(path = "/{warehouseID}")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> updateWarehouseById(@PathVariable(name = "warehouseID") Long warehouseID,
                                                      @RequestBody WarehouseDto dto) {
        if(dto==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("warehouse input cannot be null")
                            .responseObj("warehouse input cannot be null")
                            .build());
        }
        if(warehouseID==0L){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("warehouse ID cannot be 0")
                            .responseObj("warehouse ID cannot be 0")
                            .build());
        }
        if(dto!=null && (dto.getName()==null || dto.getName().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("warehouse Name cannot be null")
                            .responseObj("warehouse Name cannot be null")
                            .build());
        }

        if(dto!=null && (dto.getAddress()==null || dto.getAddress().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("warehouse Address cannot be null")
                            .responseObj("warehouse Address cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getPincode()==null || dto.getPincode().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("warehouse pincode cannot be null")
                            .responseObj("warehouse pincode cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getStatus()==null || dto.getStatus().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("warehouse status cannot be null")
                            .responseObj("warehouse status cannot be null")
                            .build());
        }
        if(dto!=null && (dto.getLocation()==null || dto.getLocation().isBlank())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST.name())
                            .msg("warehouse location cannot be null")
                            .responseObj("warehouse location cannot be null")
                            .build());
        }
        Optional<Warehouse> updatedWareHouse= this.wareHouseService.update(warehouseID, dto);
        if(updatedWareHouse.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("WareHouse updated successfully")
                            .responseObj(updatedWareHouse.get())
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("WareHouse was not found")
                            .responseObj("WareHouse was not found")
                            .build());
        }

    }

    @GetMapping(path = "/{warehouseID}")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getWarehouseById(@PathVariable(name = "warehouseID") Long warehouseID) {
        Optional<Warehouse> warehouse= this.wareHouseService.getWarehouseById(warehouseID);
        if(warehouse.isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.OK.value())
                            .status(HttpStatus.OK.name())
                            .msg("Got the Warehouse by ID")
                            .responseObj(warehouse.get())
                            .build());
        }else{
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(GlobalResponseDTO.builder()
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .status(HttpStatus.NO_CONTENT.name())
                            .msg("warehouse was not found")
                            .responseObj("warehouse was not found")
                            .build());
        }

    }

    @GetMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull ResponseEntity<?> getWarehouseList() {

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the warehouse list")
                        .responseObj(this.wareHouseService.getAllWarehouseDetails())
                        .build());
    }

    @DeleteMapping(value = { "/{warehouseID}" })
    public @NotNull ResponseEntity<?> deleteProduct(@PathVariable(name = "warehouseID")Long warehouseID) {
        this.wareHouseService.deleteWarehouseById(warehouseID);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .status(HttpStatus.NO_CONTENT.name())
                        .msg("warehouse Deleted Successfully")
                        .responseObj("warehouse Deleted Successfully")
                        .build());
    }

    @PutMapping(value = { "/status/{warehouseID}" })
    public @NotNull ResponseEntity<?> updateProduct(@PathVariable(name = "warehouseID")Long warehouseID) {
        Warehouse updatedWareHouse = wareHouseService.updateWarehouseStatus(warehouseID);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("WareHouse Status updated Successfully")
                        .responseObj(updatedWareHouse)
                        .build());
    }
}
