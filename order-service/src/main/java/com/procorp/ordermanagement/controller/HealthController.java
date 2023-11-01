package com.procorp.ordermanagement.controller;

import com.procorp.ordermanagement.dto.GlobalResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {


    @GetMapping(path = "/healthz")
    public ResponseEntity<?> getHealth(){

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("service was up and running")
                        .responseObj("Running")
                        .build());
       // return new ResponseEntity<>(HttpStatus.OK);
    }

}
