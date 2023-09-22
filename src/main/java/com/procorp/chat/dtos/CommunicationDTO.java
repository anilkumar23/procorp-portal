package com.procorp.chat.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
@ToString
public class CommunicationDTO {
    private int chatId;
    private int memberId;
    private String memberName;
    private ChatDto chatHistory;
}
