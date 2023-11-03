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
public class UserResponseDTO {
    private long memberId;
    private String mobileNumber;
    private String email;
    private String userToken;
    private boolean isEmailVerified;
    private boolean isMobileNoVerified;
    private String userName;
    private String imageURL;
}
