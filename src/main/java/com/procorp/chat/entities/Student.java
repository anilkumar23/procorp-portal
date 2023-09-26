package com.procorp.chat.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String fullName;

    private LocalDate dateOfBirth;

    private String gender;

    private String mobileNumber;

    private String email;

    private String password;

    private LocalDate registrationDate;

    public Student(String fullName, String mobileNumber, String gender, String email, String password, LocalDate dateOfBirth,LocalDate registrationDate) {
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
    }
}
