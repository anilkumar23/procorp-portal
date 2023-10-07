package com.procorp.chat.dtos;

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

    private String fullName;

    private LocalDate dateOfBirth;

    private String gender;

    private String mobileNumber;

    private String email;

    private String password;

    private LocalDate registrationDate;

    private byte[] imageData;

    private String collegeName;

    private String companyName;
}
