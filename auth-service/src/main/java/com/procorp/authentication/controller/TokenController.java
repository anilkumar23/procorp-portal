package com.procorp.authentication.controller;

import com.procorp.authentication.dao.UserRepository;
import com.procorp.authentication.exception.GlobalResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> getAuthToken(@RequestParam String email){
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(GlobalResponseDTO.builder()
						.statusCode(HttpStatus.OK.value())
						.status(HttpStatus.OK.name())
						.msg("Generated Token successfully")
						.responseObj(repo.getTokenByUsername(email))
						.build());

		//return repo.getTokenByUsername(email);
	}
}
