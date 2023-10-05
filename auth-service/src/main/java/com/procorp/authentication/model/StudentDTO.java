package com.procorp.authentication.model;

public class StudentDTO {
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public StudentDTO() {
	}

	public StudentDTO(String email) {
		this.email = email;
	}
}
