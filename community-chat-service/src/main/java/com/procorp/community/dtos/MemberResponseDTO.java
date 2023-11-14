package com.procorp.community.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO {

    private long memberId;

    private String firstName;
    private String lastName;

    private String dateOfBirth;

    private String gender;

    private String mobileNumber;

    private String email;

    private String password;

    private String registrationDate;

    private String collegeName;

    private String companyName;
    private String userToken;
    private Boolean isEmailVerified;
    private Boolean isMobileNoVerified;
    private String userName;
    private String imageURL;
}
