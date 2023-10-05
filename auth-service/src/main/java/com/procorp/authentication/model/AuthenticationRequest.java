package com.procorp.authentication.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthenticationRequest {
	private String email;
	public AuthenticationRequest(String email, String password) {
		super();
		this.email = email;
	}
	
	public AuthenticationRequest()
	{
		System.out.println();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
