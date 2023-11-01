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
public class GoogleUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String mobileNumber;

    private String email;

    private String userName;

    private String tokenId;
    private String uuid;
    private String imageURL;

    private Boolean isEmailVerified;

    private Boolean isMobileNoVerified;
}
