package com.procorp.chat.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class MemberDTO {

    @NotEmpty(message = "The full name is required.")
    @Size(min = 10, max = 100, message = "The length of full name must be between 2 and 100 characters.")
    private String fullName;

//    @NotEmpty(message = "The date Of Birth is required.")
    private LocalDate dateOfBirth;

    @NotEmpty(message = "The gender is required.")
    private String gender;

    @NotEmpty(message = "The mobileNumber is required.")
//    @Column(name = "MOBILENUMBER", unique = true, nullable = false, length = 10)
    private String mobileNumber;

    @NotEmpty(message = "The email is required.")
    private String email;

    @NotEmpty(message = "The password is required.")
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationDate;

    private EducationDetailsDTO educationDetails;

    private WorkDetailsDTO workDetailsDTO;

//    private String address;
//
//    private String username;



}
