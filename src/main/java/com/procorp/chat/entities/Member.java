package com.procorp.chat.entities;
import jakarta.persistence.*;
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

    @Lob
    @Column(name = "imagedata", length = 1000)
    private byte[] imageData;

    private String userName;
    private Boolean isEmailVerified;
    private Boolean isMobileNoVerified;

    public Member(Long memberId, String firstName, byte[] imageData) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.imageData = imageData;
    }
}
