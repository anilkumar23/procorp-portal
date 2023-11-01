package com.procorp.authentication.controller;

import com.procorp.authentication.dao.UserDao;
import com.procorp.authentication.dao.UserRepository;
import com.procorp.authentication.exception.GlobalResponseDTO;
import com.procorp.authentication.model.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-service")
public class ResourceController {
	@Autowired
	UserDao repo;
	@RequestMapping("/isAuthenticated")
	public boolean isAuthenticated(@RequestParam String token){
	 	return repo.findByTokenId(token).isPresent();
	}
	@RequestMapping("/getAuthToken")
	public String getAuthToken(@RequestParam String email){
		return repo.getTokenByEmail(email);
	}

}
