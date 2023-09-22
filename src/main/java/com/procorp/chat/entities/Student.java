package com.procorp.chat.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;

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

    private String studentName;

    private String mobileNumber;

    private String address;

    private String username;

    private String email;

    private String password;

    public Student(String studentName, String mobileNumber, String address, String username, String email, String password) {
        this.studentName = studentName;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
