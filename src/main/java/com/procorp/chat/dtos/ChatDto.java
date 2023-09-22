package com.procorp.chat.dtos;


import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatDto {
    private int memberId;
    private int memberName;
    private Timestamp date;
    private String conversation;
}
