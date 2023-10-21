package com.procorp.chat.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class MemberDTO {

    @NotEmpty(message = "The first name is required.")
    @Size(min = 1, max = 20, message = "The length of first name must be at least 1 character")
    private String firstName;
    @NotEmpty(message = "The last name is required.")
    @Size(min = 1, max = 20, message = "The length of last name must be at least 1 character")
    private String lastName;
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
