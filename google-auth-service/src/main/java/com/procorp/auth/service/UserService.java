package com.procorp.auth.service;

import com.procorp.auth.dto.Provider;
import com.procorp.auth.entity.User;
import com.procorp.auth.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private WebClient authClient;
	
	public String processOAuthPostLogin(String username) {
		User existUser = repo.getUserByUsername(username);
		if (existUser == null) {
			User newUser = new User();
			newUser.setUsername(username);
			newUser.setProvider(Provider.GOOGLE);
			newUser.setEnabled(true);

			Mono<Object> flag = authClient.get().uri("/generateAuthToken?email=" + username)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToMono(new ParameterizedTypeReference<Object>() {});
				String token = Objects.requireNonNull(flag.block()).toString().replace("{","").replace("}","").replace("token=","");
				newUser.setToken(token);

			repo.save(newUser);
			
			System.out.println("Created new user: " + username);
			return token;
		}
		return "";
	}

	public String getAuthToken(String userName){
		return repo.getTokenByUsername(userName);
	}
}
