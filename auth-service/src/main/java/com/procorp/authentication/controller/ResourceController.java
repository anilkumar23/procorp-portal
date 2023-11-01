package com.procorp.authentication.controller;

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
	UserRepository repo;
	@RequestMapping("/isAuthenticated")
	public boolean isAuthenticated(@RequestParam String token){
		/*return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(GlobalResponseDTO.builder()
						.statusCode(HttpStatus.OK.value())
						.status(HttpStatus.OK.name())
						.msg("User Authenticated successfully")
						.responseObj(repo.findByToken(token).isPresent())
						.build());*/
	 //	return StringUtils.hasText(repo.getTokenByUsername(email));
		return repo.findByToken(token).isPresent();
	}

	/*public boolean isAuthenticated(@RequestParam String token) {
		return repo.findByToken(token).isPresent();
	}*/
	@RequestMapping("/getAuthToken")
	public String getAuthToken(@RequestParam String email){

		/*return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(GlobalResponseDTO.builder()
						.statusCode(HttpStatus.OK.value())
						.status(HttpStatus.OK.name())
						.msg("Generated Token successfully")
						.responseObj(repo.getTokenByUsername(email))
						.build());*/
		return repo.getTokenByUsername(email);
	}

}
