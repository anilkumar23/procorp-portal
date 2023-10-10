package com.procorp.chat.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OTPDetails {
    @Id
    private int id;
    private String email;
    private String mobileNo;
    private String mobileOTP;
    private String emailOTP;
    private long mobileOtpExpiry;
    private long emailOtpExpiry;

}
