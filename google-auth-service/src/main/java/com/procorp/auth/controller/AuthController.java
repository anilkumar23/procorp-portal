package com.procorp.auth.controller;

import com.procorp.auth.exception.GlobalResponseDTO;
import com.procorp.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    UserService service;

    @RequestMapping("/getAuthToken")
    public ResponseEntity<?> getAuthToken(@RequestParam String email){

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(GlobalResponseDTO.builder()
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK.name())
                        .msg("Got the Auth Token by using email")
                        .responseObj(service.getAuthToken(email))
                        .build());

       // return service.getAuthToken(email);
    }
}
