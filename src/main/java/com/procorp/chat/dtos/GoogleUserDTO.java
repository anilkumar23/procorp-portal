package com.procorp.chat.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class GoogleUserDTO {
    private String userName;

    private String mobileNumber;

    private String email;
    private boolean isEmailVerified;
    private boolean isMobileVerified;
    private String uuid;
    private String imageURL;
}
