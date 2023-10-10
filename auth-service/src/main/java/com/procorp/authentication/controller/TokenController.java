package com.procorp.authentication.controller;

import com.procorp.authentication.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/getAuthToken")
public class TokenController {
	@Autowired
	UserRepository repo;
	@GetMapping
	public String getAuthToken(@RequestParam String email){
		return repo.getTokenByUsername(email);
	}

}
