package com.procorp.chat.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Communication {
    @Id
    private int chatId;
    private int memberId;
    private String memberName;
    private String chatHistory;
}