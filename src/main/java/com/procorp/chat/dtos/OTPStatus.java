package com.procorp.chat.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OTPStatus {
    public enum isExpired{YES, NO, INVALID};
    private String message;
    public OTPStatus(isExpired isExpired, String s) {
        this.setMessage(s);
    }
}
