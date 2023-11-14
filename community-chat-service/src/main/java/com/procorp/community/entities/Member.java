package com.procorp.community.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Builder
public class Member {
    @Id
    private Long memberId;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String gender;

    private String mobileNumber;

    private String email;

    private String password;

    private LocalDate registrationDate;

    private String collegeName;

    private String companyName;

    private String schoolName;

    private String imageUrl;

    private String userName;
    private Boolean isEmailVerified;
    private Boolean isMobileNoVerified;
}
