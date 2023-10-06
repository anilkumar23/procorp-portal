package com.procorp.auth.controller;

import com.procorp.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    UserService service;

    @RequestMapping("/getAuthToken")
    public String getAuthToken(@RequestParam String email){
        return service.getAuthToken(email);
    }
}
