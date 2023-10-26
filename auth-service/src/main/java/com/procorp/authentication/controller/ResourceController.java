package com.procorp.authentication.controller;

import com.procorp.authentication.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-service")
public class ResourceController {
	@Autowired
	UserRepository repo;
	@RequestMapping("/isAuthenticated")
	public boolean isAuthenticated(@RequestParam String token){
	 	return repo.findByToken(token).isPresent();
	}
	@RequestMapping("/getAuthToken")
	public String getAuthToken(@RequestParam String email){
		return repo.getTokenByUsername(email);
	}

}
