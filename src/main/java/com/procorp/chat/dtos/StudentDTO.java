package com.procorp.chat.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Date;

@Data
public class StudentDTO {

    @NotEmpty(message = "The full name is required.")
    @Size(min = 10, max = 100, message = "The length of full name must be between 2 and 100 characters.")
    private String studentName;

    private String mobileNumber;

    private String address;

    private String username;

    private String email;

    private String password;
}
